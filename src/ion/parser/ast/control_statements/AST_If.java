package ion.parser.ast.control_statements;

import ion.parser.ast.AST;
import ion.parser.ast.ASTType;
import ion.parser.ast.expressions.AST_Expression;

public class AST_If extends AST {
    
    private static int nextId = 0;
    private static int makeId() { return nextId++; }

    private final int id;
    private final AST_Expression condition;
    private final AST ifBlock;
    private final AST elseBlock; 

    public AST_If(AST_Expression condition, AST ifBlock, AST elseBlock) {
        super(ASTType.IF);

        id = makeId();

        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    public int getId() { return id; }

    @Override
    public String generateAssembly() {
        String asm = "";
        asm += condition.generateAssembly();
        asm += "    cmp rax, 0\n";
        asm += "    je if_" + id + "_else\n";
        asm += ifBlock.generateAssembly();
        asm += "    jmp if_" + id + "_end\n";
        if(elseBlock != null) {
            asm += "if_" + id + "_else:\n";
            asm += elseBlock.generateAssembly();
        }
        asm += "if_" + id + "_end:\n";
        return asm;
    }

    @Override
    public String toString() {
        return super.toString() + " condition=" + condition + " ifBlock=" + ifBlock + (elseBlock != null ? " elseBlock=" + elseBlock + ">" : ">");
    }

}
