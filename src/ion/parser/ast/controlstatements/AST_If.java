package ion.parser.ast.controlstatements;

import ion.parser.ast.AST;
import ion.parser.ast.ASTType;
import ion.parser.ast.expressions.AST_Expression;

public class AST_If extends AST {

    private final AST_Expression condition;
    private final AST ifBlock;
    private final AST elseBlock; 

    public AST_If(AST_Expression condition, AST ifBlock, AST elseBlock) {
        super(ASTType.IF);

        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String generateAssembly() {
        System.err.println("Not implemented: AST_If.generateAssembly()");
        System.exit(1);
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " condition=" + condition + " ifBlock=" + ifBlock + (elseBlock != null ? " elseBlock=" + elseBlock + ">" : ">");
    }

}
