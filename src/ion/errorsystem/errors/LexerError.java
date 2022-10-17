package ion.errorsystem.errors;

public abstract class LexerError extends Error {

    @Override
    public String toString() {
        return "[Lexer] ERROR: ";
    }

}
