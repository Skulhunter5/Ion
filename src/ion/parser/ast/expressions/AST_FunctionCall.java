package ion.parser.ast.expressions;

import ion.parser.ast.ASTType;
import ion.utils.DataType;
import ion.utils.Function;
import ion.utils.Variable;

// TODO: rewrite AST_FunctionCall in some way
public class AST_FunctionCall extends AST_Expression {

    private final Function function;
    private final Variable variable;

    public AST_FunctionCall(Function function) {
        super(ASTType.FUNCTION_CALL);
        this.function = function;
        this.variable = null;
    }

    public AST_FunctionCall(Variable variable) {
        super(ASTType.FUNCTION_CALL);
        this.function = null;
        this.variable = variable;
    }

    @Override
    public String generateAssembly() {
        if(function != null) return "    call func_" + function.getId() + "\n";
        String asm = "";
        asm += "    mov rax, [var_" + variable.getId() + "]\n";
        asm += "    call rax\n";
        return asm;
    }

    @Override
    public DataType getResultingDataType() {
        return null;
    }
    
    @Override
    public String toString() {
        return super.toString() + (function != null ? " function=" + function : " variable=" + variable) + ">";
    }
    
}
