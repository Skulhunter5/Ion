package ion;

import java.util.ArrayList;

import ion.frontends.AssemblyFrontend;
import ion.lexer.Lexer;
import ion.lexer.Token;
import ion.parser.Parser;
import ion.parser.ParserResult;

public class Compiler {

    public static String compileToAssembly(String filename, String sourceCode) {
        ArrayList<Token> tokens = new Lexer(filename, sourceCode).lex();
        // for(Token token : tokens) System.out.println(token);
        ParserResult parserResult = new Parser(tokens).parse();
        //System.out.println(parserResult);
        String assemblyCode = new AssemblyFrontend(parserResult).generate();
        return assemblyCode; // CWD
    }

}
