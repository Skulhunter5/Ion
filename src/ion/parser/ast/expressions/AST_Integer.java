package ion.parser.ast.expressions;

import ion.parser.ast.ASTType;

public class AST_Integer extends AST_Expression {
    
    public final String value;

    public AST_Integer(String value) {
        super(ASTType.INTEGER);
        this.value = value;
    }

    @Override
    public String generateAssembly() {
        return "    mov rax, " + Integer.parseInt(value) + "\n";
    }

    @Override
    public String toString() {
        return super.toString() + " value=\"" + value + "\">";
    }

}
