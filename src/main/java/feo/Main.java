package feo;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        try {
            final ArrayDeclaration arrayDeclaration = new Parser().parse(System.in);
            DisplayArrayDeclaration.display(arrayDeclaration);
        } catch (final ParseException e) {
            System.err.println("During parsing an error occurred: " + e.getMessage());
        }
    }
}