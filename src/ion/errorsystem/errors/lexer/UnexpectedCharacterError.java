package ion.errorsystem.errors.lexer;

import ion.errorsystem.errors.LexerError;
import ion.utils.Position;
import ion.utils.Utils;

public class UnexpectedCharacterError extends LexerError {
    
    private final char c;
    private final Position position;

    public UnexpectedCharacterError(char c, Position position) {
        this.c = c;
        this.position = position;
    }

    @Override
    public String toString() {
        return super.toString() + "Unexpected character: '" + Utils.escapeCharacter(c) + "' (" + ((int) c) + ") at " + position;
    }

}
