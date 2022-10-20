package ion.parser.ast;

public abstract class AST {

    public final ASTType type;

    public AST(ASTType type) {
        this.type = type;
    }

    public abstract String generateAssembly();
    public abstract void typeCheck();

    @Override
    public String toString() {
        return "<" + type;
    }

}
