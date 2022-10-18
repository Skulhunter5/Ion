package ion.parser.ast;

public enum ASTType {
    // Block
    BLOCK,
    // Control Statements
    IF, WHILE, DO_WHILE,
    // Values
    VARIABLE_ACCESS, INTEGER, FLOAT,
    // Assignment
    ASSIGNMENT,
    // Arithmetic operation
    BINARY_OPERATION,
    // Function call
    FUNCTION_CALL,
    // PRINT (TEMP)
    PRINT;
}
