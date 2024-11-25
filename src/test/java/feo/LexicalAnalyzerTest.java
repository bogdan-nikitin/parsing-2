package feo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class LexicalAnalyzerTest {
    LexicalAnalyzer run(final String input) throws ParseException {
        return new LexicalAnalyzer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    void checkSequence(final String input, final Token ... tokens) throws ParseException {
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

    @Test
    void testEmpty() throws ParseException {
        checkSequence("");
    }
}