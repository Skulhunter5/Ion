package ion.parser.ast.expressions;

import ion.lexer.TokenType;
import ion.parser.ast.ASTType;
import ion.utils.Constants;
import ion.utils.DataType;

public class AST_BinaryOperation extends AST_Expression {

    public final TokenType operator;
    public final AST_Expression a;
    public final AST_Expression b;

    public AST_BinaryOperation(TokenType operator, AST_Expression a, AST_Expression b) {
        super(ASTType.BINARY_OPERATION);
        this.operator = operator;
        this.a = a;
        this.b = b;
    }

    @Override
    public String generateAssembly() {
        String asm = "";
        asm += a.generateAssembly();
        asm += "    push rax\n";
        asm += b.generateAssembly();
        asm += "    mov rbx, rax\n";
        asm += "    pop rax\n";
        switch(operator) {
            case PLUS:
                asm += "    add rax, rbx\n";
                break;
            case MINUS:
                asm += "    sub rax, rbx\n";
                break;
            case STAR:
                asm += "    xor rdx, rdx\n";
                asm += "    mul rbx\n";
                break;
            case SLASH:
                asm += "    xor rdx, rdx\n";
                asm += "    div rbx\n";
                break;
            case PERCENT:
                asm += "    xor rdx, rdx\n";
                asm += "    div rbx\n";
                asm += "    mov rax, rdx\n";
                break;
            default:
                // Comparison operator tokens
                if(operator.isComparisonOperator()) {
                    asm += "    cmp rax, rbx\n";
                    asm += "    mov rax, 0\n";
                    asm += "    set" + Constants.COMPARISON_SUFFIXES.get(operator) + " al\n";
                    break;
                }
                // Logical operator tokens
                if(operator == TokenType.LAND) { // TODO check if this is necessary
                    asm += "    xor rcx, rcx\n";
                    asm += "    cmp rax, 0\n";
                    asm += "    setne cl\n";
                    asm += "    xor rax, rax\n";
                    asm += "    cmp rbx, 0\n";
                    asm += "    setne al\n";
                    asm += "    and rax, rcx\n";
                    break;
                } else if(operator == TokenType.LOR) {
                    asm += "    xor rcx, rcx\n";
                    asm += "    cmp rax, 0\n";
                    asm += "    setne cl\n";
                    asm += "    xor rax, rax\n";
                    asm += "    cmp rbx, 0\n";
                    asm += "    setne al\n";
                    asm += "    or rax, rcx\n";
                    break;
                }
                // Unreachable
                System.err.println("Unreachable: 5184284327");
                System.exit(1);
        }
        return asm;
    }

    @Override
    public DataType getResultingDataType() {
        DataType aType = a.getResultingDataType();
        DataType bType = b.getResultingDataType();
        boolean isEqual = aType.equals(bType);
        if(operator.isLogicalOperator()) {
            if(aType.type == DataType.BOOLEAN && bType.type == DataType.BOOLEAN) return DataType.I_BOOLEAN;
        } else if(operator.isArithmeticOperator()) {
            if(isEqual) {
                if(aType.type == DataType.UINT64) return DataType.I_UINT64;
            }
        }
        System.out.println("Invalid DataTypes for BinaryOperation: operator=" + operator + "a=" + aType + " b=" + bType);
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " operator=" + operator + " a=" + a + " b=" + b + ">";
    }

}
