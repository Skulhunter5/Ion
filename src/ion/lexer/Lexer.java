package ion.lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import ion.errorsystem.ErrorSystem;
import ion.errorsystem.errors.lexer.UnexpectedCharacterError;
import ion.utils.Position;

import static java.util.Map.entry;

public class Lexer {

    // @formatter:off

    private static final Map<Character, TokenType> ONE_CHAR_TOKENS = new HashMap<>(Map.ofEntries(
        // SEMICOLON
        entry(';', TokenType.SEMICOLON),
        // SINGLE_EQ
        entry('=', TokenType.ASSIGN),
        // Comparison operator tokens
        entry('<', TokenType.LT),
        entry('>', TokenType.GT),
        // Arithmetic operator tokens
        entry('+', TokenType.PLUS),
        entry('-', TokenType.MINUS),
        entry('*', TokenType.STAR),
        entry('/', TokenType.SLASH),
        entry('%', TokenType.PERCENT),
        // Parenthesis, Braces and Brackes
        entry('(', TokenType.LPAREN), entry(')', TokenType.RPAREN),
        entry('{', TokenType.LBRACE), entry('}', TokenType.RBRACE),
        entry('[', TokenType.LBRACK), entry(']', TokenType.RBRACK)
    ));

    private static final Map<String, TokenType> TWO_CHAR_TOKENS = new HashMap<>(Map.ofEntries(
        // Comparison operator tokens
        entry("==", TokenType.EQ),
        entry("!=", TokenType.NEQ),
        entry("<=", TokenType.LTEQ),
        entry(">=", TokenType.GTEQ),
        // Logical operator tokens
        entry("&&", TokenType.LAND),
        entry("||", TokenType.LOR),
        // Arithmetic operator-equals tokens
        entry("+=", TokenType.PLUS_EQ),
        entry("-=", TokenType.MINUS_EQ),
        entry("*=", TokenType.STAR_EQ),
        entry("/=", TokenType.SLASH_EQ),
        entry("%=", TokenType.PERCENT_EQ)
    ));

    private static final HashSet<String> KEYWORDS = new HashSet<>(Arrays.asList(
        "var",
        "func"
    ));

    // @formatter:on

    private final String sourceCode;
    private int position;
    private char c;

    private final String file;
    private int line = 1, column = 1;

    public Lexer(String filename, String sourceCode) {
        this.file = filename;

        this.sourceCode = sourceCode;
        position = 0;
        c = (position < this.sourceCode.length()) ? this.sourceCode.charAt(position) : '\0';
    }

    private char advance() {
        position++;
        if(c == '\n') {
            this.column = 1;
            this.line++;
        } else this.column++;
        c = (position < sourceCode.length()) ? sourceCode.charAt(position) : '\0';
        return c;
    }

    private char advance(int n) {
        for(int i = 0; i < n; i++) advance();
        return c;
    }

    private Position currentPosition() {
        return new Position(this.file, this.line, this.column);
    }

    private Position makePosition(int column) {
        return new Position(this.file, this.line, column);
    }

    private char advanceWithLast() {
        char last = c;
        advance();
        return last;
    }

    private Token advanceWith(Token token) {
        advance();
        return token;
    }

    private char peek(int offset) {
        return (position + offset < sourceCode.length()) ? sourceCode.charAt(position + offset) : '\0';
    }

    private Token parseNumber() {
        String number = "";
        int isFloatingPoint = 0;
        int startingColumn = this.column;
        while(position < sourceCode.length() && (Character.isDigit(c) || c == '.')) {
            if(c == '.') {
                if(isFloatingPoint == 1) ErrorSystem.AddError_s(new UnexpectedCharacterError(c, currentPosition()));
                isFloatingPoint += 1;
            }
            number += advanceWithLast();
        }
        return new Token(isFloatingPoint > 0 ? TokenType.FLOAT : TokenType.INTEGER, number, makePosition(startingColumn));
    }

    private Token parseIdentifier() {
        String identifier = "";
        int startingColumn = this.column;
        while(position < sourceCode.length() && (Character.isLetterOrDigit(c) || c == '_')) identifier += advanceWithLast();
        return new Token(KEYWORDS.contains(identifier) ? TokenType.KEYWORD : TokenType.IDENTIFIER, identifier, makePosition(startingColumn));
    }

    public ArrayList<Token> lex() {
        ArrayList<Token> tokens = new ArrayList<Token>();

        while(position < sourceCode.length()) {
            if(Character.isWhitespace(c)) advance();
            else if(Character.isDigit(c)) tokens.add(parseNumber());
            else if(Character.isLetter(c) || c == '_') tokens.add(parseIdentifier());
            else {
                String twoChars = "" + c + peek(1);
                if(TWO_CHAR_TOKENS.containsKey(twoChars)) {
                    advance(2);
                    tokens.add(new Token(TWO_CHAR_TOKENS.get(twoChars), makePosition(this.column - 2)));
                } else if(ONE_CHAR_TOKENS.containsKey(c)) tokens.add(advanceWith(new Token(ONE_CHAR_TOKENS.get(c), currentPosition())));
                else ErrorSystem.AddError_i(new UnexpectedCharacterError(c, currentPosition()));
            }
        }
        tokens.add(new Token(TokenType.EOF, currentPosition()));

        return tokens;
    }

}
