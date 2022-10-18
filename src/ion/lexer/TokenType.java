package ion.lexer;

public enum TokenType {
    
    // Syntax tokens
    // - SEMICOLON
    SEMICOLON,
    // - Parenthesis, Braces and Brackes
    LPAREN, RPAREN,
    LBRACE, RBRACE,
    LBRACK, RBRACK,
    // IDENTIFIER
    IDENTIFIER,
    KEYWORD,
    // Numbers
    INTEGER, FLOAT,
    // SINGLE_EQ
    ASSIGN,
    // Comparison operator tokens
    // - Equality operator tokens
    EQ, NEQ,
    // - Relational operator tokens
    LT, GT, LTEQ, GTEQ,
    // Logical operator tokens
    LAND, LOR,
    // Arithmetic operator tokens
    // - Additive operator tokens
    PLUS, MINUS,
    // - Multiplicative operator tokens
    STAR, SLASH, PERCENT,

    // Arithmetic operator-equals tokens
    PLUS_EQ, MINUS_EQ, STAR_EQ, SLASH_EQ, PERCENT_EQ,
    // EOF
    EOF;

    public boolean isEqualityOperator() {
        return this == EQ || this == NEQ;
    }

    public boolean isRelationalOperator() {
        return this.ordinal() >= LT.ordinal() && this.ordinal() <= GTEQ.ordinal();
    }

    public boolean isComparisonOperator() {
        return this.ordinal() >= EQ.ordinal() && this.ordinal() <= GTEQ.ordinal();
    }

    public boolean isLogicalOperator() {
        return this == LAND || this == LOR;
    }

    public boolean isArithmeticOperator() {
        return this.ordinal() >= PLUS.ordinal() && this.ordinal() <= PERCENT.ordinal();
    }

    public boolean isBinaryOperator() {
        return this.ordinal() >= EQ.ordinal() && this.ordinal() <= PERCENT.ordinal();
    }

    public boolean isAssignmentOperator() {
        return this.ordinal() >= PLUS_EQ.ordinal() && this.ordinal() <= PERCENT_EQ.ordinal();
    }

}
