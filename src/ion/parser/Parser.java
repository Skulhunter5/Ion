package ion.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ion.parser.ast.*;
import ion.parser.ast.control_statements.AST_DoWhile;
import ion.parser.ast.control_statements.AST_If;
import ion.parser.ast.control_statements.AST_While;
import ion.parser.ast.expressions.*;
import ion.utils.Function;
import ion.utils.Variable;
import ion.errorsystem.ErrorSystem;
import ion.errorsystem.errors.parser.UnexpectedTokenError;
import ion.errorsystem.errors.parser.IdentifierRedeclarationError;
import ion.errorsystem.errors.parser.UndeclaredIdentifierError;
import ion.lexer.Token;
import ion.lexer.TokenType;

import static java.util.Map.entry;

public class Parser {

    // @formatter:off

    private static final HashMap<TokenType, Integer> OPERATION_LEVELS = new HashMap<>(Map.ofEntries(
        // Logical operator tokens
        entry(TokenType.LOR, 1),
        entry(TokenType.LAND, 2),
        // Comparison operator tokens
        // - Equality operator tokens
        entry(TokenType.EQ, 3),
        entry(TokenType.NEQ, 3),
        // - Relational operator tokens
        entry(TokenType.LT, 4),
        entry(TokenType.GT, 4),
        entry(TokenType.LTEQ, 4),
        entry(TokenType.GTEQ, 4),
        // Arithmetic operator tokens
        // - Additive operator tokens
        entry(TokenType.PLUS, 5),
        entry(TokenType.MINUS, 5),
        // - Multiplicative operator tokens
        entry(TokenType.STAR, 6),
        entry(TokenType.SLASH, 6),
        entry(TokenType.PERCENT, 6)
    ));

    // @formatter:on

    private final ArrayList<Token> tokens;
    private Token current;
    private int tokenIndex;

    private final HashMap<String, Variable> variables;
    private final HashMap<String, Function> functions;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        tokenIndex = 0;
        current = tokens.get(0);

        variables = new HashMap<String, Variable>();
        functions = new HashMap<String, Function>();
    }

    private Token advance() {
        tokenIndex++;
        current = (tokenIndex < tokens.size()) ? tokens.get(tokenIndex) : tokens.get(tokens.size() - 1);
        return current;
    }

    private Token advanceWithLast() {
        Token last = current;
        advance();
        return last;
    }

    private void eat(TokenType type) {
        if(current.type != type)
            ErrorSystem.AddError_i(new UnexpectedTokenError(type.getSourceString(), current.type.getSourceString(), current.position)); // TODO: maybe change to ErrorSystem.AddError_s()
        advance();
    }

    private boolean isKeyword(String keyword) {
        return current.type == TokenType.KEYWORD && current.value.equals(keyword);
    }

    public ParserResult parse() {
        parseDeclaration();
        return new ParserResult(variables, functions);
    }

    private void parseDeclaration() {
        while(current.type != TokenType.EOF) {
            if(current.type == TokenType.KEYWORD) {
                if(current.value.equals("func")) {
                    advance(); // KEYWORD "func"
                    Token identifier = current;
                    eat(TokenType.IDENTIFIER);
                    eat(TokenType.LPAREN);
                    eat(TokenType.RPAREN);
                    AST body = parseBlock();
                    registerFunction(identifier, body);
                    continue;
                } else if(current.value.equals("var")) {
                    advance(); // KEYWORD "var"
                    Token identifier = current;
                    eat(TokenType.IDENTIFIER);
                    registerVariable(identifier);
                    eat(TokenType.SEMICOLON);
                    continue;
                } else; // TODO: implement error for this problem (ErrorSystem.AddError_i(new UnexpectedTokenError(current, "declaration keyword"));)
            } else ErrorSystem.AddError_i(new UnexpectedTokenError(TokenType.KEYWORD.getSourceString(), current.type.getSourceString(), current.position)); // TODO: maybe change to ErrorSystem.AddError_s()
        }
    }

    private AST parseBlock() {
        ArrayList<AST> statements = new ArrayList<AST>();

        boolean isMultiline = false;
        if(current.type == TokenType.LBRACE) {
            advance();
            isMultiline = true;
        }

        if(isMultiline) {
            while(current.type != TokenType.EOF && current.type != TokenType.RBRACE) {
                AST statement = parseStatement();
                if(statement != null) statements.add(statement);
            }

            if(current.type == TokenType.EOF); // TODO: implement error for this problem (ErrorSystem.AddError_i(new MissingCharacterError('}', current.Position));)

            eat(TokenType.RBRACE);
        } else return parseStatement();

        return new AST_Block(statements);
    }

    private AST parseStatement() {
        if(current.type == TokenType.SEMICOLON) {
            advance();
            return null;
        }

        if(current.type == TokenType.LBRACE) return parseBlock();

        if(current.type == TokenType.KEYWORD) {
            switch(current.value) {
                case "var": { // TODO: rework this parsing step
                    advance(); // KEYWORD "var"
                    Token identifier = current;
                    eat(TokenType.IDENTIFIER);
                    Variable variable = registerVariable(identifier);
                    if(current.type == TokenType.SEMICOLON) {
                        advance(); // SEMICOLON
                        return null;
                    }
                    eat(TokenType.ASSIGN);
                    AST_Expression valueExpression = parseExpression(false);
                    eat(TokenType.SEMICOLON);
                    return new AST_Assignment(TokenType.ASSIGN, variable, valueExpression);
                }
                case "if": {
                    advance(); // KEYWORD "if"
                    eat(TokenType.LPAREN);
                    AST_Expression condition = parseExpression();
                    eat(TokenType.RPAREN);
                    AST ifBlock = parseBlock();
                    AST elseBlock = null;
                    if(isKeyword("else")) {
                        advance(); // IDENTIFIER "else"
                        elseBlock = parseBlock();
                    }
                    return new AST_If(condition, ifBlock, elseBlock);
                }
                case "while": {
                    advance(); // KEYWORD "while"
                    eat(TokenType.LPAREN);
                    AST_Expression condition = parseExpression();
                    eat(TokenType.RPAREN);
                    AST body = parseBlock();
                    return new AST_While(condition, body);
                }
                case "do": {
                    advance(); // KEYWORD "do"
                    AST body = parseBlock();
                    if(current.type != TokenType.KEYWORD || !"while".equals(current.value))
                        ErrorSystem.AddError_i(new UnexpectedTokenError("while", current.getSourceString(), current.position));
                    advance(); // KEYWORD "while"
                    eat(TokenType.LPAREN);
                    AST_Expression condition = parseExpression();
                    eat(TokenType.RPAREN);
                    eat(TokenType.SEMICOLON); // TODO: check if the do-while-loop requires the semicolon itself or if it comes from something else like a normal statement for example
                    return new AST_DoWhile(condition, body);
                }
                default: {
                    // TODO: implement error for this case
                    System.err.println("Not implemented: 91367592374");
                    return null;
                }
            }
        }

        if(current.type == TokenType.IDENTIFIER && current.value.equals("print")) { // TODO remove
            advance(); // IDENTIFIER "print"
            AST_Expression expression = parseExpression();
            eat(TokenType.SEMICOLON);
            return new AST_Print(expression);
        }

        if(current.type == TokenType.SEMICOLON) {
            advance();
            return null;
        }

        AST_Expression expression = parseExpression();
        eat(TokenType.SEMICOLON);
        return expression;
    }

    private AST_Expression parseExpression() {
        return parseExpression(true);
    }

    private AST_Expression parseExpression(boolean greedyAssignment) {
        ArrayList<AST_Expression> particles = new ArrayList<>();
        ArrayList<TokenType> operators = new ArrayList<>();
        particles.add(parseExpressionParticle(greedyAssignment));

        while(current.type.isBinaryOperator()) {
            operators.add(advanceWithLast().type);
            particles.add(parseExpressionParticle(greedyAssignment));
        }

        reduceExpression(particles, operators, 0, 0);

        return particles.get(0);
    }

    // first call: reduceExpression(particles, operators, 0, 0);
    private void reduceExpression(ArrayList<AST_Expression> particles, ArrayList<TokenType> operators, int start, int level) {
        if(operators.size() == 0) return;
        if(level == 0) level = OPERATION_LEVELS.get(operators.get(0));
        while(particles.size() > start + 1) {
            if(OPERATION_LEVELS.get(operators.get(start)) < level) level = OPERATION_LEVELS.get(operators.get(0));
            if(operators.size() > start + 1 && OPERATION_LEVELS.get(operators.get(start + 1)) > level) {
                reduceExpression(particles, operators, start + 1, OPERATION_LEVELS.get(operators.get(start + 1)));
                continue;
            }
            particles.set(start, new AST_BinaryOperation(operators.get(start), particles.get(start), particles.get(start + 1)));
            particles.remove(start + 1);
            operators.remove(start);
        }
    }

    private AST_Expression parseExpressionParticle(boolean greedyAssignment) {
        switch(current.type) {
            case LPAREN: {
                advance(); // LPAREN
                AST_Expression expression = parseExpression();
                eat(TokenType.RPAREN);
                return expression;
            }
            case IDENTIFIER: {
                Token identifier = advanceWithLast(); // IDENTIFIER

                if(current.type.isAssignmentOperator()) {
                    TokenType assignmentType = advanceWithLast().type; // <ASSIGNMENT_OPERATOR>
                    AST_Expression valueExpression = greedyAssignment ? parseExpression(false) : parseExpressionParticle(false);
                    return new AST_Assignment(assignmentType, getVariable(identifier), valueExpression);
                } else if(current.type == TokenType.LPAREN) {
                    eat(TokenType.LPAREN);
                    eat(TokenType.RPAREN);
                    return new AST_FunctionCall(getFunction(identifier));
                }

                return new AST_VariableAccess(getVariable(identifier));
            }
            case INTEGER:
                return new AST_Integer(advanceWithLast().value);
            default: {
                System.out.println("Unexpected token: " + current);
                System.exit(1);
                return null; // Unreachable
            }
        }
    }

    private Variable registerVariable(Token identifier) {
        if(variables.containsKey(identifier.value))
            ErrorSystem.AddError_i(new IdentifierRedeclarationError(variables.get(identifier.value).getIdentifierToken(), identifier.position)); // TODO: change to ErrorSystem.AddError_s()
        Variable variable = new Variable(identifier);
        variables.put(identifier.value, variable);
        return variable;
    }

    private Variable getVariable(Token identifier) {
        if(!variables.containsKey(identifier.value))
            ErrorSystem.AddError_i(new UndeclaredIdentifierError(identifier)); // TODO: change to ErrorSystem.AddError_s()
        return variables.get(identifier.value);
    }

    private Function registerFunction(Token identifier, AST body) {
        if(variables.containsKey(identifier.value))
            ErrorSystem.AddError_i(new IdentifierRedeclarationError(functions.get(identifier.value).getIdentifierToken(), identifier.position)); // TODO: change to ErrorSystem.AddError_s()
        Function function = new Function(identifier, body);
        functions.put(identifier.value, function);
        return function;
    }

    private Function getFunction(Token identifier) {
        if(!functions.containsKey(identifier.value))
            ErrorSystem.AddError_i(new UndeclaredIdentifierError(identifier)); // TODO: change to ErrorSystem.AddError_s()
        return functions.get(identifier.value);
    }

}
