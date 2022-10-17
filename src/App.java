import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ion.Compiler;

public class App {

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.err.println("You at least need to specify the input file");
            return;
        }

        String inFilename = args[0];
        String outFilename = inFilename + ".asm";
        String cwd = Paths.get("").toAbsolutePath().toString();

        int i = 1;
        while(i < args.length) {
            if(args[i].equals("-o")) {
                i++;
                outFilename = args[i++];
            } else if(args[i].equals("-cwd")) {
                i++;
                cwd = args[i++];
            } else {
                System.out.println("Invalid argument: '" + args[i] + "'");
                return;
            }
        }

        File cwdFile = new File(cwd);
        if(!cwdFile.exists()) {
            System.err.println("CWD not found");
            return;
        }
        if(!cwdFile.isDirectory()) { System.err.println("CWD must be a directory"); }

        File inFile = new File(cwd + "\\" + inFilename);
        if(!inFile.exists()) {
            System.err.println("Input file not found");
            return;
        }

        File outFile = new File(cwd + "\\" + outFilename);

        String code = readFileToString(inFile);
        String assembly = Compiler.compileToAssembly(inFile.getAbsolutePath(), code);
        writeStringToFile(outFile, assembly);

        // test();
    }

    /*
     * private static <T> boolean arrayContains(T[] array, T type) { for(int i = 0;
     * i < array.length; i++) if(array[i] == type) return true; return false; }
     */

    /*
     * private static boolean isArithmeticOperator(TokenType type) { return
     * type.ordinal() >= TokenType.PLUS.ordinal() && type.ordinal() <=
     * TokenType.PERCENT.ordinal(); }
     */

    /*
     * private static void test() { int n = Integer.MAX_VALUE; HashSet<TokenType>
     * hashset = new HashSet<>(Arrays.asList(TokenType.PLUS, TokenType.MINUS,
     * TokenType.STAR, TokenType.SLASH, TokenType.PERCENT)); TokenType[] array =
     * {TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH,
     * TokenType.PERCENT}; ArrayList<TokenType> arraylist = new
     * ArrayList<>(List.of(TokenType.PLUS, TokenType.MINUS, TokenType.STAR,
     * TokenType.SLASH, TokenType.PERCENT));
     * 
     * long start = System.currentTimeMillis(); for(int i = 0; i < n; i++)
     * if(hashset.contains(TokenType.EOF)); System.out.println("HashSet<TokenType> "
     * + (System.currentTimeMillis() - start));
     * 
     * start = System.currentTimeMillis(); for(int i = 0; i < n; i++)
     * if(arrayContains(array, TokenType.EOF)); System.out.println("TokenType[] " +
     * (System.currentTimeMillis() - start));
     * 
     * start = System.currentTimeMillis(); for(int i = 0; i < n; i++)
     * if(arraylist.contains(TokenType.EOF));
     * System.out.println("ArrayList<TokenType> " + (System.currentTimeMillis() -
     * start));
     * 
     * start = System.currentTimeMillis(); for(int j = 0; j < 1; j++) for(int i = 0;
     * i < n; i++) if(isArithmeticOperator(TokenType.EOF));
     * System.out.println("x>=first&&x<=last " + (System.currentTimeMillis() -
     * start)); }
     */

    public static String readFileToString(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    public static void writeStringToFile(File file, String str) throws IOException {
        Files.write(Paths.get(file.getAbsolutePath()), str.getBytes());
    }

}
