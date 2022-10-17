package ion.errorsystem.errors.frontend;

import ion.errorsystem.errors.FrontendError;

public class MissingMainFunctionError extends FrontendError {
    
    @Override
    public String toString() {
        return super.toString() + "No main function has been declared";
    }

}
