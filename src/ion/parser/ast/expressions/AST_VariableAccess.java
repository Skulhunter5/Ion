package ion.parser.ast.expressions;

import ion.parser.ast.ASTType;
import ion.utils.DataType;
import ion.utils.Variable;

public class AST_VariableAccess extends AST_Expression {
    
    private final Variable variable;

    public AST_VariableAccess(Variable variable) {
        super(ASTType.VARIABLE_ACCESS);
        this.variable = variable;
    }

    @Override
    public String generateAssembly() {
        return "    mov rax, [var_" + variable.getId() + "]\n";
    }

    @Override
    public DataType getResultingDataType() {
        return variable.getDataType();
    }

    @Override
    public String toString() {
        return super.toString() + " variable=" + variable + ">";
    }

}
