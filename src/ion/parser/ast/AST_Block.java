package ion.parser.ast;

import java.util.ArrayList;

public class AST_Block extends AST {
    
    public final ArrayList<AST> statements;

    public AST_Block(ArrayList<AST> statements) {
        super(ASTType.BLOCK);
        this.statements = statements;
    }

    @Override
    public String generateAssembly() {
        String asm = "";
        for(AST statement : statements) asm += statement.generateAssembly();
        return asm;
    }

    @Override
    public String toString() {
        ArrayList<String> statementStrings = new ArrayList<>();
        for(AST child : statements) statementStrings.add(child.toString());
        return super.toString() + " statements=[" + String.join(",", statementStrings) + "]>";
    }

}
