package ion.errorsystem.errors.parser;

import ion.errorsystem.errors.ParserError;
import ion.lexer.Token;

public class UndeclaredIdentifierError extends ParserError {

    private final Token identifier;

    public UndeclaredIdentifierError(Token identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return super.toString() + "Undeclared identifier '" + identifier.value + "' at " + identifier.position;
    }

}
