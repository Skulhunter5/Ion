package ion.lexer;

import ion.utils.Position;

public class Token {
    
    public final TokenType type;
    public final String value;
    public final Position position;

    public Token(TokenType type, Position position) {
        this.type = type;
        this.value = null;
        this.position = position;
    }

    public Token(TokenType type, String value, Position position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    public String getSourceString() {
        if(value != null) return value;
        return type.getSourceString();
    }

    @Override
    public String toString() {
        return "<TOKEN " + type + (value != null ? (" '" + value + "'") : "") + " position='" + position + "'>";
    }

}
