package ion.parser.ast.expressions;

import ion.parser.ast.ASTType;
import ion.utils.DataType;
import ion.utils.Function;

public class AST_FunctionCall extends AST_Expression {

    private final Function function;

    public AST_FunctionCall(Function function) {
        super(ASTType.FUNCTION_CALL);
        this.function = function;
    }

    @Override
    public String generateAssembly() {
        return "    call func_" + function.getId() + "\n";
    }

    @Override
    public DataType getResultingDataType() {
        return null;
    }
    
    @Override
    public String toString() {
        return super.toString() + " function=" + function + ">";
    }
    
}
