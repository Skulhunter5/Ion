package ion.parser;

import java.util.HashMap;

import ion.utils.Function;
import ion.utils.Variable;

public class ParserResult {
    
    private final HashMap<String, Variable> variables;
    private final HashMap<String, Function> functions;

    public ParserResult(HashMap<String, Variable> variables, HashMap<String, Function> functions) {
        this.variables = variables;
        this.functions = functions;
    }

    public HashMap<String, Variable> getVariables() { return variables; }
    public HashMap<String, Function> getFunctions() { return functions; }

    @Override
    public String toString() {
        // Generate Strings for variables
        String[] variableStrings = new String[variables.size()];
        int i = 0;
        for(Variable variable : variables.values()) variableStrings[i++] = variable.toString();
        // Generate Strings for functions
        String[] functionStrings = new String[functions.size()];
        i = 0;
        for(Function function : functions.values()) functionStrings[i++] = function.toString();
        // return result of ParserResult.toString()
        return "ParserResult<variables=[" + String.join(",", variableStrings) + "], functions=[" + String.join(",", functionStrings) + "]>";
    }

}
