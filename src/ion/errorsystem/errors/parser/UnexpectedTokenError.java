package ion.errorsystem.errors.parser;

import ion.errorsystem.errors.ParserError;
import ion.utils.Position;

public class UnexpectedTokenError extends ParserError {

    private final String expected;
    private final String got;
    private final Position position;

    public UnexpectedTokenError(String expected, String got, Position position) {
        this.expected = expected;
        this.got = got;
        this.position = position;
    }

    @Override
    public String toString() {
        return super.toString() + "Expected '" + expected + "' but got '" + got + "' at " + position;
    }
    
}
