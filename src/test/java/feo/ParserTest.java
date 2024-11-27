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
        Assertions.assertEquals(parse(input), new ArrayDeclaration(variableName, new ElementType(elementType)));
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
    @DisplayName("Array as element type")
    void testArrayOfArrays() {
        checkException("var array: Array<Array>;");
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
}