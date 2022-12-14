package ion.parser.ast.expressions;

import ion.parser.ast.ASTType;
import ion.utils.DataType;
import ion.utils.Function;

public class AST_FunctionAccess extends AST_Expression {

    private final Function function;

    public AST_FunctionAccess(Function function) {
        super(ASTType.FUNCTION_ACCESS);
        this.function = function;
    }

    @Override
    public String generateAssembly() {
        return "    mov rax, func_" + function.getId() + "\n";
    }

    @Override
    public DataType getResultingDataType() {
        return DataType.I_FUNCTION;
    }
    
    @Override
    public String toString() {
        return super.toString() + " function=" + function + ">";
    }
}
