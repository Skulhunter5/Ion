package ion.parser.ast.control_statements;

import ion.parser.ast.AST;
import ion.parser.ast.ASTType;
import ion.parser.ast.expressions.AST_Expression;

public class AST_DoWhile extends AST {
    
    private static int nextId = 0;
    private static int makeId() { return nextId++; }

    private final int id;
    private final AST_Expression condition;
    private final AST body;

    public AST_DoWhile(AST_Expression condition, AST body) {
        super(ASTType.DO_WHILE);

        id = makeId();

        this.condition = condition;
        this.body = body;
    }

    public int getId() { return id; }

    @Override
    public String generateAssembly() {
        String asm = "";
        asm += "dowhile_" + id + "_start:\n";
        asm += body.generateAssembly();
        asm += condition.generateAssembly();
        asm += "    cmp rax, 0\n";
        asm += "    jne dowhile_" + id + "_start\n";
        asm += "dowhile_" + id + "_end:\n";
        return asm;
    }

    @Override
    public void typeCheck() {
        condition.typeCheck();
        body.typeCheck();
    }

    @Override
    public String toString() {
        return super.toString() + " condition=" + condition + " body=" + body + ">";
    }
}
