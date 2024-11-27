package feo;

import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

public class GraphBuilder {
    public static Graph build(final ArrayDeclaration arrayDeclaration) {
        return graph().directed().with(
                node("ArrayDeclaration")
                        .link(node(arrayDeclaration.variableName()))
                        .link(build(arrayDeclaration.elementType()))
        );
    }

    public static Node build(final ElementType elementType) {
        final String identifier = elementType.getIdentifier();
        if (identifier != null) {
            return node(identifier);
        }
        return node("Array").link(build(elementType.getChild()));
    }
}
