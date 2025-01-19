package feo.parsergen;


import feo.ArrayDeclaration;
import feo.DisplayArrayDeclaration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.in.transferTo(outputStream);
            final ArrayLexer lexer = new ArrayLexer(outputStream.toString(StandardCharsets.UTF_8));
            final ArrayDeclaration arrayDeclaration = new ArrayParser(lexer).run().declaration;
            DisplayArrayDeclaration.display(arrayDeclaration);
        } catch (final ArrayLexerException e) {
            System.err.println("An error occurred in the lexer: " + e.getMessage());
        } catch (final ArrayParserException e) {
            System.err.println("An error occurred during parsing: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error during reading: " + e.getMessage());
        }
    }
}
