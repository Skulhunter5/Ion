package ion.utils;

import java.util.HashMap;
import java.util.Map;

import ion.lexer.TokenType;

import static java.util.Map.entry;

public class Constants {

    // @formatter:off

    public static final HashMap<TokenType, String> COMPARISON_SUFFIXES = new HashMap<>(Map.ofEntries(
        // Comparison operator tokens
        entry(TokenType.EQ, "e"),
        entry(TokenType.NEQ, "ne"),
        entry(TokenType.LT, "l"),
        entry(TokenType.GT, "g"),
        entry(TokenType.LTEQ, "le"),
        entry(TokenType.GTEQ, "ge")
    ));

    // @formatter:on

}
