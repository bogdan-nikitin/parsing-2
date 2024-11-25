package feo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

class LexicalAnalyzerTest {
    final Token[] SIMPLE_TOKENS = {
            Token.VAR,
            Token.ARRAY,
            Token.LANGLEBRACKET,
            Token.RANGLEBRACKET,
            Token.COLON,
            Token.SEMICOLON
    };

    LexicalAnalyzer run(final String input) throws ParseException {
        return new LexicalAnalyzer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    void checkSequence(final String input, final Token... tokens) throws ParseException {
        final LexicalAnalyzer lex = run(input);
        final List<Token> tokenList = new ArrayList<>();
        for (int i = 0; i < tokens.length; ++i) {
            lex.nextToken();
            tokenList.add(lex.curToken());
        }
        lex.nextToken();
        Assertions.assertEquals(lex.curToken(), Token.END);
        Assertions.assertArrayEquals(tokenList.toArray(), tokens);
    }

    Token ident(final String value) {
        return new Token(TokenType.IDENT, value);
    }

    void checkIdent(final String ident) throws ParseException {
        checkSequence(ident, ident(ident));
    }

    @Test
    void testEmpty() throws ParseException {
        checkSequence("");
    }

    @Test
    void testSimpleTokens() throws ParseException {
        for (Token token : SIMPLE_TOKENS) {
            checkSequence(token.getTokenType().toString(), token);
        }
    }

    @Test
    void testIdent() throws ParseException {
        checkIdent("foobar");
        checkIdent("FooBar");
        checkIdent("FooBar");
        checkIdent("_foobar");
        checkIdent("_______");
        checkIdent("foobar_");
        checkIdent("foo_bar");
        checkIdent("_foo_bar_");
        checkIdent("foo42");
        checkIdent("foo42bar");
    }

    @Test
    void testValid() throws ParseException {
        checkSequence("var foobar42: Array<Int>",
                Token.VAR, ident("foobar42"), Token.COLON, Token.ARRAY,
                Token.LANGLEBRACKET, ident("Int"), Token.RANGLEBRACKET);
    }
}