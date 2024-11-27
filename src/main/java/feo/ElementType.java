package feo;

import java.util.Objects;

public class ElementType {
    private final String identifier;
    private final ElementType child;

    public ElementType(final String identifier) {
        this.identifier = identifier;
        this.child = null;
    }

    public ElementType(final ElementType child) {
        this.identifier = null;
        this.child = child;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ElementType getChild() {
        return child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElementType that)) return false;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getChild(), that.getChild());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getChild());
    }
}
