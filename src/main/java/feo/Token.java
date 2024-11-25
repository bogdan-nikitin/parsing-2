package feo;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Token that) {
            return that.tokenType == tokenType && Objects.equals(that.value, value);
        }
        return false;
    }

    public final static Token VAR = new Token(TokenType.VAR);
    public final static Token ARRAY = new Token(TokenType.ARRAY);
    public final static Token LANGLEBRACKET = new Token(TokenType.LANGLEBRACKET);
    public final static Token RANGLEBRACKET = new Token(TokenType.RANGLEBRACKET);
    public final static Token END = new Token(TokenType.END);
    public final static Token COLON = new Token(TokenType.COLON);
    public final static Token SEMICOLON = new Token(TokenType.SEMICOLON);
}
