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

    List<Token> tokens(final String input) throws ParseException {
        final LexicalAnalyzer lex = run(input);
        final List<Token> tokens = new ArrayList<>();
        while (true) {
            lex.nextToken();
            if (lex.curToken().getTokenType() == TokenType.END) {
                return tokens;
            }
            tokens.add(lex.curToken());
        }
    }

    void checkSequence(final String input, final Token ... tokens) throws ParseException {
        Assertions.assertArrayEquals(tokens(input).toArray(), tokens);
    }

    @Test
    void testEmpty() throws ParseException {
        checkSequence("");
    }
}