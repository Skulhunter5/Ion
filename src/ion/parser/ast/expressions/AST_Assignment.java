package ion.parser.ast.expressions;

import ion.lexer.TokenType;
import ion.parser.ast.ASTType;
import ion.utils.Variable;

public class AST_Assignment extends AST_Expression {
    
    public final TokenType assignmentType;
    public final Variable variable;
    public final AST_Expression valueExpression;

    public AST_Assignment(TokenType assignmentType, Variable variable, AST_Expression valueExpression) {
        super(ASTType.ASSIGNMENT);
        this.assignmentType = assignmentType;
        this.variable = variable;
        this.valueExpression = valueExpression;
    }

    @Override
    public String generateAssembly() {
        String asm = "";
        asm += valueExpression.generateAssembly();
        switch(assignmentType) {
            case ASSIGN:
                asm += "    mov [var_" + variable.getId() + "], rax\n";
                break;
            case PLUS_EQ:
                asm += "    add [var_" + variable.getId() + "], rax\n";
                asm += "    mov rax, [var_" + variable.getId() + "]\n";
                break;
            case MINUS_EQ:
                asm += "    sub [var_" + variable.getId() + "], rax\n";
                asm += "    mov rax, [var_" + variable.getId() + "]\n";
                break;
            case STAR_EQ:
                asm += "    mov rbx, rax\n";
                asm += "    mov rax, [var_" + variable.getId() + "]\n";
                asm += "    xor rdx, rdx\n";
                asm += "    mul rbx\n";
                asm += "    mov [var_" + variable.getId() + "], rax\n";
                break;
            case SLASH_EQ:
                asm += "    mov rbx, rax\n";
                asm += "    mov rax, [var_" + variable.getId() + "]\n";
                asm += "    xor rdx, rdx\n";
                asm += "    div rbx\n";
                asm += "    mov [var_" + variable.getId() + "], rax\n";
                break;
            case PERCENT_EQ:
                asm += "    mov rbx, rax\n";
                asm += "    mov rax, [var_" + variable.getId() + "]\n";
                asm += "    xor rdx, rdx\n";
                asm += "    div rbx\n";
                asm += "    mov rax, rdx\n";
                asm += "    mov [var_" + variable.getId() + "], rax\n";
                break;
            default:
                System.err.println("Unreachable: 546135465145");
                System.exit(1);
        }
        return asm;
    }

    @Override
    public String toString() {
        return super.toString() + " type=" + assignmentType + "variable=" + variable + " valueExpression=" + valueExpression + ">";
    }

}
