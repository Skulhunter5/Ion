package ion.utils;

import static java.util.Map.entry;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    
    private static final Map<Character, String> ESCAPED_CHARACTERS = new HashMap<>(Map.ofEntries(
            entry('\0', "\\0"),
            entry('\r', "\\r"),
            entry('\t', "\\t"),
            entry('\b', "\\b"),
            entry('\f', "\\f"),
            entry('\n', "\\n"),
            entry('\"', "\\\""),
            entry('\'', "\\'")
    ));

    public static String escapeCharacter(char c) {
        return ESCAPED_CHARACTERS.getOrDefault(c, ""+c);
    }

    public static String escapeCharacters(String str) {
        String res = "";
        for(char c : str.toCharArray()) res += ESCAPED_CHARACTERS.getOrDefault(c, ""+c);
        return res;
    }

}
