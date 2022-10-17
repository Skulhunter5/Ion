package ion.errorsystem.errors.parser;

import ion.errorsystem.errors.ParserError;
import ion.lexer.TokenType;
import ion.utils.Position;

public class UnexpectedTokenError extends ParserError {

    private final TokenType expectedType;
    private final TokenType gotType;
    private final Position position;

    public UnexpectedTokenError(TokenType expectedType, TokenType gotType, Position position) {
        this.expectedType = expectedType;
        this.gotType = gotType;
        this.position = position;
    }

    @Override
    public String toString() {
        return super.toString() + "Expected token of type " + expectedType + " but got type " + gotType + " at " + position;
    }
    
}
