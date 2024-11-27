package feo;

import java.io.InputStream;
import java.text.ParseException;

public class Parser {
    private LexicalAnalyzer lex;

    public Parser() {
    }

    private void throwExpected(final String expected) throws ParseException {
        throw new ParseException(expected + " expected at position", lex.curPos());
    }

    private ElementType T() throws ParseException {
        lex.nextToken();
        final ElementType elementType;
        switch (lex.curToken().getTokenType()) {
            case ARRAY -> {
                expect(TokenType.LANGLEBRACKET);
                elementType = new ElementType(T());
                expect(TokenType.RANGLEBRACKET);
            }
            case IDENT -> {
                elementType = new ElementType(lex.curToken().getValue());
            }
            default -> {
                throwExpected(TokenType.ARRAY + " or " + TokenType.IDENT);
                return null;
            }
        }
        return elementType;
    }

    public ArrayDeclaration parse(final InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        expect(TokenType.VAR);
        expect(TokenType.IDENT);
        final String variableName = lex.curToken().getValue();
        expect(TokenType.COLON);
        expect(TokenType.ARRAY);
        expect(TokenType.LANGLEBRACKET);
        final ElementType elementType = T();
        expect(TokenType.RANGLEBRACKET);
        expect(TokenType.SEMICOLON);
        expect(TokenType.END);
        return new ArrayDeclaration(variableName, elementType);
    }

    private void expect(final TokenType tokenType) throws ParseException {
        lex.nextToken();
        if (lex.curToken().getTokenType() != tokenType) {
            throwExpected(tokenType.toString());
        }
    }
}
