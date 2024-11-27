package feo;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;


import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

public class GraphBuilder {
    private int lastNode = 0;

    public Graph build(final ArrayDeclaration arrayDeclaration) {
        return graph().directed().with(
                nextNode("ArrayDeclaration")
                        .link(nextNode(arrayDeclaration.variableName()))
                        .link(build(arrayDeclaration.elementType()))
        );
    }

    public Node build(final ElementType elementType) {
        final String identifier = elementType.getIdentifier();
        if (identifier != null) {
            return nextNode(identifier);
        }
        return nextNode("Array").link(build(elementType.getChild()));
    }

    private Node nextNode(final String name) {
        lastNode += 1;
        return node(String.valueOf(lastNode)).with(Label.of(name));
    }
}
