package ion.frontends;

import java.util.HashMap;

import ion.parser.ParserResult;
import ion.utils.Function;
import ion.utils.Variable;

public abstract class Frontend<T> {
    
    protected final HashMap<String, Variable> variables;
    protected final HashMap<String, Function> functions;

    public Frontend(ParserResult parserResult) {
        this.variables = parserResult.getVariables();
        this.functions = parserResult.getFunctions();
    }

    public abstract T generate();

}
