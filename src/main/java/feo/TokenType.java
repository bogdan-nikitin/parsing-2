package feo;

public enum TokenType {
    VAR("var"),
    ARRAY("Array"),
    LANGLEBRACKET("<"),
    RANGLEBRACKET(">"),
    IDENT("identifier"),
    END("end of file"),
    COLON(":"),
    SEMICOLON(";");

    private final String toString;

    TokenType(final String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}
