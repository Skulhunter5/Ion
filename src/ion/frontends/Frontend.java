package ion.frontends;

import java.util.HashMap;

import ion.parser.ParserResult;
import ion.parser.ast.AST_Block;
import ion.utils.Function;
import ion.utils.Variable;

public abstract class Frontend<T> {
    
    protected final HashMap<String, Variable> variables;
    protected final AST_Block declaration;
    protected final HashMap<String, Function> functions;

    public Frontend(ParserResult parserResult) {
        this.variables = parserResult.getVariables();
        this.declaration = parserResult.getDeclaration();
        this.functions = parserResult.getFunctions();
    }

    public abstract T generate();

}
