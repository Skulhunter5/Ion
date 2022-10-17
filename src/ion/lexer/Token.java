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

    @Override
    public String toString() {
        return "<TOKEN " + type + (value != null ? (" '" + value + "'>") : ">");
    }

}
