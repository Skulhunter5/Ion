package ion.utils;

import ion.lexer.Token;

public class Variable {
    
    private static int nextId = 0;
    private static int makeId() { return nextId++; }

    private final int id;
    private final Token identifierToken;
    private final DataType dataType;

    public Variable(Token identifierToken, DataType dataType) {
        id = makeId();
        this.identifierToken = identifierToken;
        this.dataType = dataType;
    }

    public int getId() { return id; }
    public Token getIdentifierToken() { return identifierToken; }
    public DataType getDataType() { return dataType; }

    @Override
    public String toString() {
        return "<VARIABLE id=" + id + " identifier='" + identifierToken.value + "', dataType=" + dataType.toString() + ">";
    }

}
