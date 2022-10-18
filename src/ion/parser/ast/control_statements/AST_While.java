package ion.parser.ast.control_statements;

import ion.parser.ast.AST;
import ion.parser.ast.ASTType;
import ion.parser.ast.expressions.AST_Expression;

public class AST_While extends AST {
    
    private static int nextId = 0;
    private static int makeId() { return nextId++; }

    private final int id;
    private final AST_Expression condition;
    private final AST body;

    public AST_While(AST_Expression condition, AST body) {
        super(ASTType.WHILE);

        id = makeId();

        this.condition = condition;
        this.body = body;
    }

    public int getId() { return id; }

    @Override
    public String generateAssembly() {
        String asm = "";
        asm += "while_" + id + "_start:\n";
        asm += condition.generateAssembly();
        asm += "    cmp rax, 0\n";
        asm += "    je while_" + id + "_end\n";
        asm += body.generateAssembly();
        asm += "    jmp while_" + id + "_start\n";
        asm += "while_" + id + "_end:\n";
        return asm;
    }

    @Override
    public String toString() {
        return super.toString() + " condition=" + condition + " body=" + body + ">";
    }

}
