package ion.parser.ast;

public abstract class AST {

    public final ASTType type;

    public AST(ASTType type) {
        this.type = type;
    }

    public abstract String generateAssembly();

    @Override
    public String toString() {
        return "<" + type;
    }

}
