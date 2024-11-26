package feo;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        try {
            final ArrayDeclaration array = new Parser().parse(System.in);
            System.out.printf("digraph { ArrayDeclaration -> \"%s\" ArrayDeclaration -> \"%s\" }%n",
                    array.variableName(),
                    array.elementType());
        } catch (ParseException e) {
            System.err.println("During parsing an error occurred: " + e);
        }
    }
}