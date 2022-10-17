package ion.utils;

import ion.lexer.Token;
import ion.parser.ast.AST;

public class Function {
    
    private static int nextId = 0;
    private static int makeId() { return nextId++; }

    private final int id;
    private final Token identifierToken;
    private final AST body;

    public Function(Token identifierToken, AST body) {
        id = makeId();
        this.identifierToken = identifierToken;
        this.body = body;
    }

    public int getId() { return id; }
    public Token getIdentifierToken() { return identifierToken; }
    public AST getBody() { return body; }

    public String generateAssembly() {
        String asm = "";
        asm += "func_" + id + ":\n";
        asm += body.generateAssembly();
        asm += "    ret\n";
        return asm;
    }

    @Override
    public String toString() {
        return "<FUNCTION id=" + id + " identifier='" + identifierToken.value + "' body=" + body + ">";
    }

}
