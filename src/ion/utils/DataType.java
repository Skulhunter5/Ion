package ion.utils;

import ion.lexer.Token;
import ion.lexer.TokenType;

public class DataType {
    
    public static final int VOID = 0;
    public static final int UINT64 = 1;
    public static final int BOOLEAN = 2;
    public static final int FUNCTION = 3;

    public static final DataType I_VOID = new DataType(VOID);
    public static final DataType I_UINT64 = new DataType(UINT64);
    public static final DataType I_BOOLEAN = new DataType(BOOLEAN);
    public static final DataType I_FUNCTION = new DataType(FUNCTION);

    public final int type;

    public DataType(int type) {
        this.type = type;
    }

    public static DataType parseDataType(Token token) {
        assert token.type == TokenType.IDENTIFIER;
        // TODO: improve this code
        switch(token.value) {
            case "uint64": return DataType.I_UINT64;
            case "bool": return DataType.I_BOOLEAN;
            case "func": return DataType.I_FUNCTION;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof DataType)) return false;
        DataType other = (DataType) obj;
        return other.type == this.type;
    }

    private static final String[] dataTypeStrings = new String[] {
        "void",
        "uint64",
        "bool",
        "func"
    };

    @Override
    public String toString() {
        return dataTypeStrings[this.type];
    }

}
