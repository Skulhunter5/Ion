package ion.utils;

import ion.lexer.Token;

public class Variable {
    
    private static int nextId = 0;
    private static int makeId() { return nextId++; }

    private final int id;
    private final Token identifierToken;

    public Variable(Token identifierToken) {
        id = makeId();
        this.identifierToken = identifierToken;
    }

    public int getId() { return id; }
    public Token getIdentifierToken() { return identifierToken; }

    @Override
    public String toString() {
        return "<VARIABLE id=" + id + " identifier='" + identifierToken.value + "'>";
    }

}
