package ion.lexer;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

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

    // Source strings

    private static final Map<TokenType, String> sourceStrings = new HashMap<>(Map.ofEntries(
        // Syntax tokens
        // - SEMICOLON
        entry(TokenType.SEMICOLON, ";"),
        // - Parenthesis, Braces and Brackes
        entry(TokenType.LPAREN, "("),
        entry(TokenType.RPAREN, ")"),
        entry(TokenType.LBRACE, "{"),
        entry(TokenType.RBRACE, "}"),
        entry(TokenType.LBRACK, "["),
        entry(TokenType.RBRACK, "]"),
        // IDENTIFIER
        entry(TokenType.IDENTIFIER, "IDENTIFIER"),
        entry(TokenType.KEYWORD, "KEYWORD"),
        // Numbers
        entry(TokenType.INTEGER, "INTEGER"),
        entry(TokenType.FLOAT, "FLOAT"),
        // SINGLE_EQ
        entry(TokenType.ASSIGN, "="),
        // Comparison operator tokens
        entry(TokenType.EQ, "=="),
        entry(TokenType.NEQ, "!="),
        entry(TokenType.LT, "<"),
        entry(TokenType.GT, ">"),
        entry(TokenType.LTEQ, "<="),
        entry(TokenType.GTEQ, ">="),
        // Logical operator tokens
        entry(TokenType.LAND, "&&"),
        entry(TokenType.LOR, "||"),
        // Arithmetic operator tokens
        entry(TokenType.PLUS, "+"),
        entry(TokenType.MINUS, "-"),
        entry(TokenType.STAR, "*"),
        entry(TokenType.SLASH, "/"),
        entry(TokenType.PERCENT, "%"),
        // Arithmetic operator-equals tokens
        entry(TokenType.PLUS_EQ, "+="),
        entry(TokenType.MINUS_EQ, "-="),
        entry(TokenType.STAR_EQ, "*="),
        entry(TokenType.SLASH_EQ, "/="),
        entry(TokenType.PERCENT_EQ, "%="),
        // EOF
        entry(TokenType.EOF, "EOF")
    ));

    public String getSourceString() {
        return sourceStrings.get(this);
    }

}
