package feo;

import java.io.InputStream;
import java.text.ParseException;

public class Parser {
    private LexicalAnalyzer lex;

    public Parser() {
    }

    public ArrayDeclaration parse(final InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        expect(TokenType.VAR);
        expect(TokenType.IDENT);
        final String variableName = lex.curToken().getValue();
        expect(TokenType.COLON);
        expect(TokenType.ARRAY);
        expect(TokenType.LANGLEBRACKET);
        expect(TokenType.IDENT);
        final String elementType = lex.curToken().getValue();
        expect(TokenType.RANGLEBRACKET);
        expect(TokenType.SEMICOLON);
        expect(TokenType.END);
        return new ArrayDeclaration(variableName, elementType);
    }

    private void expect(final TokenType tokenType) throws ParseException {
        lex.nextToken();
        if (lex.curToken().getTokenType() != tokenType) {
            throw new ParseException(tokenType.toString() + " expected at position", lex.curPos());
        }
    }
}
