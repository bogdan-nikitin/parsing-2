package feo;

public class Token {
    private final TokenType tokenType;
    private final String value;

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public Token(TokenType tokenType) {
        this(tokenType, null);
    }

    public String getValue() {
        return value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
