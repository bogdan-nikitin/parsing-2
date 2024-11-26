package feo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

    List<Token> tokens(final String input) throws ParseException {
        final LexicalAnalyzer lex = run(input);
        final List<Token> tokenList = new ArrayList<>();
        while (true) {
            lex.nextToken();
            if (lex.curToken() == Token.END) {
                break;
            }
            tokenList.add(lex.curToken());
        }
        return tokenList;
    }

    void checkSequence(final String input, final Token... tokens) throws ParseException {
        Assertions.assertArrayEquals(tokens(input).toArray(), tokens);
    }

    Token ident(final String value) {
        return new Token(TokenType.IDENT, value);
    }

    void checkIdent(final String ident) throws ParseException {
        checkSequence(ident, ident(ident));
    }

    void checkException(final String input) {
        Assertions.assertThrows(ParseException.class, () -> {
           checkSequence(input);
        });
    }

    @Test
    @DisplayName("Empty input")
    void testEmpty() throws ParseException {
        checkSequence("");
    }

    @Test
    @DisplayName("Input of one single token without value")
    void testSimpleTokens() throws ParseException {
        for (Token token : SIMPLE_TOKENS) {
            checkSequence(token.getTokenType().toString(), token);
        }
    }

    @Test
    @DisplayName("Check identifier in many variants")
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
    @DisplayName("Case of var and Array")
    void testCase() throws ParseException {
        checkIdent("Var");
        checkIdent("vAr");
        checkIdent("vaR");
        checkIdent("VAR");
        checkIdent("ArraY");
        checkIdent("array");
        checkIdent("ARRAY");
    }

    @Test
    @DisplayName("Prefixes of var and Array to be an identifier")
    void testPrefix() throws ParseException {
        checkIdent("v");
        checkIdent("va");
        checkIdent("varfoo");
        checkIdent("A");
        checkIdent("Arra");
        checkIdent("Arraybar");
    }

    @Test
    @DisplayName("Check array declaration")
    void testValid() throws ParseException {
        checkSequence("var foobar42: Array<Int>;",
                Token.VAR, ident("foobar42"), Token.COLON, Token.ARRAY,
                Token.LANGLEBRACKET, ident("Int"), Token.RANGLEBRACKET, Token.SEMICOLON);
    }

    @Test
    @DisplayName("Check random valid tokens")
    void testRandom() throws ParseException {
        checkSequence("Array>foobar<;;:var",
                Token.ARRAY, Token.RANGLEBRACKET, ident("foobar"), Token.LANGLEBRACKET,
                Token.SEMICOLON, Token.SEMICOLON, Token.COLON, Token.VAR
                );
    }

    @Test
    @DisplayName("Check blank characters")
    void testBlank() throws ParseException {
        checkSequence(" ");
        checkSequence("\r");
        checkSequence("\n");
        checkSequence("\t");
        checkSequence("   \r   \n\t\n\n \r\r\t       \n\r");
        checkSequence("   \r   \n\n\t  \r  var  \r\n \t foobar42   \n \r   \r\r  :\n \rArray\r<\tInt   >  \r; \n",
                Token.VAR, ident("foobar42"), Token.COLON, Token.ARRAY,
                Token.LANGLEBRACKET, ident("Int"), Token.RANGLEBRACKET, Token.SEMICOLON);
    }

    @Test
    @DisplayName("Identifier starting with number")
    void testDigitFirstIdentifier() {
        checkException("42foobar");
        checkException("var 42foobar: Array<Int>;");
        checkException("var foobar: Array<42Int>;");
    }

    @Test
    @DisplayName("Invalid characters")
    void testInvalidCharacters() {
        checkException("var foobar: Array<Int+>;");
        checkException("var$ foobar: Array<Int>;");
        checkException("var foobar: Array<Int>;%");
    }
}