package ion.parser.ast;

import ion.parser.ast.expressions.AST_Expression;
public class AST_Print extends AST {

    private final AST_Expression expression;

    public AST_Print(AST_Expression expression) {
        super(ASTType.PRINT);
        this.expression = expression;
    }

    @Override
    public String generateAssembly() {
        String asm = "";
        asm += expression.generateAssembly();
        asm += "    call put_u\n";
        return asm;
    }

    @Override
    public void typeCheck() {
        expression.typeCheck();
    }

    @Override
    public String toString() {
        return super.toString() + " expression=" + expression + ">";
    }
    
}
