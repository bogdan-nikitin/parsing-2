grammar Array;

@header {
    import feo.ArrayDeclaration;
    import feo.ElementType;
}

array returns [ArrayDeclaration declaration]: 'var' ID ':' 'Array' '<' type '>' ';'
    {
    $declaration = new ArrayDeclaration($ID.text, $type.elementType);
    };
type returns [ElementType elementType]:
    'Array' '<' type '>' { $elementType = new ElementType($type.elementType); }
    | ID { $elementType = new ElementType($ID.text); };

ID: "\\p{javaLetter}(\\p{javaLetterOrDigit}|_)*";
WS: "\\s+" -> skip;