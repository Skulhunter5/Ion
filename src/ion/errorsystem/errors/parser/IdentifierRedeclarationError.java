package ion.errorsystem.errors.parser;

import ion.errorsystem.errors.ParserError;
import ion.lexer.Token;
import ion.utils.Position;

public class IdentifierRedeclarationError extends ParserError {

    private final Token original;
    private final Position newPosition;

    public IdentifierRedeclarationError(Token original, Position newPosition) {
        this.original = original;
        this.newPosition = newPosition;
    }

    @Override
    public String toString() {
        return super.toString() + "Trying to redeclare identifier '" + original.value + "' at " + newPosition + " (originally declared at " + original.position + ")";
    }

}
