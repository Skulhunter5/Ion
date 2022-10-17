package ion.errorsystem.errors;

public class FrontendError extends Error {

    @Override
    public String toString() {
        return "[Frontend] ERROR: ";
    }

}
