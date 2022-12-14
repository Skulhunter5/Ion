package ion.parser.ast.expressions;

import ion.parser.ast.AST;
import ion.parser.ast.ASTType;
import ion.utils.DataType;

public abstract class AST_Expression extends AST {
    
    public AST_Expression(ASTType type) {
        super(type);
    }
    
    public abstract DataType getResultingDataType();

    public void typeCheck() {
        getResultingDataType();
    }

}
