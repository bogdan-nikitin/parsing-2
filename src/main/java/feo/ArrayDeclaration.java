package feo;

import java.util.Objects;

public class ArrayDeclaration {
    private final String variableName;
    private final String elementType;

    public ArrayDeclaration(final String variableName, final String elementType) {
        this.variableName = variableName;
        this.elementType = elementType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArrayDeclaration that) {
            return Objects.equals(variableName, that.variableName) && Objects.equals(elementType, that.elementType);
        }
        return false;
    }

    public String getElementType() {
        return elementType;
    }

    public String getVariableName() {
        return variableName;
    }
}
