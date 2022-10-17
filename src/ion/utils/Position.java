package ion.utils;

public class Position {
    
    private String file;
    private int line, column;

    public Position(String file, int line, int column) {
        this.file = file;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return file + ":" + line + ":" + column;
    }

}
