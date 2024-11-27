package feo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

class ParserTest {
    ArrayDeclaration parse(final String input) throws ParseException {
        return new Parser().parse(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    void checkDeclaration(final String input, final String variableName, final String elementType) throws ParseException {
        checkDeclaration(input, variableName, elementType, 0);
    }

    void checkDeclaration(final String input,
                          final String variableName,
                          final String elementTypeIdentifier,
                          final int depth) throws ParseException {
        ElementType elementType = new ElementType(elementTypeIdentifier);
        for (int i = 0; i < depth; ++i) {
            elementType = new ElementType(elementType);
        }
        Assertions.assertEquals(parse(input), new ArrayDeclaration(variableName, elementType));
    }

    void checkException(final String input) {
        Assertions.assertThrows(ParseException.class, () -> parse(input));
    }

    @Test
    @DisplayName("Simple test for valid input")
    void testValid() throws ParseException {
        checkDeclaration("var foo: Array<Int>;", "foo", "Int");
        checkDeclaration("var foo:Array<Z>;", "foo", "Z");
        checkDeclaration("var bar : Array<kgeorgiy>;", "bar", "kgeorgiy");
    }

    @Test
    @DisplayName("Test blank characters")
    void testBlank() throws ParseException {
        checkDeclaration("\n\tvar  \n\r\tfoo\n\t  :  Array  <  Int  > ;  ", "foo", "Int");
    }

    @Test
    @DisplayName("Test valid case with trailing characters")
    void testTrailing() {
        checkException("var foo: Array<Int>;;");
        checkException("var foo: Array<Int>;var");
        checkException("var foo: Array<Int>;bar");
    }

    @Test
    @DisplayName("Test missing tokens")
    void testMissing() {
        checkException("foo: Array<Int>;");
        checkException("var: Array<Int>;");
        checkException("var foo Array<Int>;");
        checkException("var foo: <Int>;");
        checkException("var foo: Array Int>;");
        checkException("var foo: Array<>;");
        checkException("var foo: Array<Int;");
        checkException("var foo: Array<Int>");
    }

    @Test
    @DisplayName("Test repeating tokens")
    void testRepeats() {
        checkException("var var foo: Array<Int>;");
        checkException("var foo bar: Array<Int>;");
        checkException("var foo: : Array<Int>;");
        checkException("var foo: Array Array<Int>;");
        checkException("var foo: Array<<Int>;");
        checkException("var foo: Array<Int>>;");
        checkException("var foo: Array<Int>;;");
    }

    @Test
    @DisplayName("Wrong brackets")
    void testBrackets() {
        checkException("var foo: Array>Int>;");
        checkException("var foo: Array<Int<;");
        checkException("var foo: Array>Int<;");
    }

    @Test
    @DisplayName("Not an identifier as element type")
    void testWrongElementType() {
        checkException("var foo: Array<var>;");
        checkException("var foo: Array<:>;");
    }

    @Test
    @DisplayName("Check nested arrays")
    void testNested() throws ParseException {
        checkDeclaration("var foo: Array<Array<Bar>>;", "foo", "Bar", 1);
        checkDeclaration("var foo: Array<Array<Array_>>;",
                "foo", "Array_", 1);
        checkDeclaration("var foo: Array<Array<Array<Array<Bar>>>>;",
                "foo", "Bar", 3);
    }

    @Test
    @DisplayName("Check invalid nested arrays")
    void testInvalidNested() {
        checkException("var foo: Array<Array>;");
        checkException("var foo: Array<<Array<Int>>>;");
        checkException("var foo: Array<Array<Int>;>;");
        checkException("var foo: Array<Array<Array<Int>>;");
        checkException("var foo: Array<Array<Array<Int>>>>;");
        checkException("var foo: Array<Array<Int<Array<Int>>>>;");
        checkException("var foo: Array<<Int>>;");
    }
}