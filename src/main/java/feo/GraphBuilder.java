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
                nextNode("S")
                        .link(nextNode("var"))
                        .link(ident(arrayDeclaration.variableName()))
                        .link(nextNode(":"))
                        .link(nextNode("Array"))
                        .link(nextNode("<"))
                        .link(build(arrayDeclaration.elementType()))
                        .link(nextNode(">"))
                        .link(nextNode(";"))
        );
    }
    private Node ident(final String identifier) {
        return nextNode("Ident " + identifier);
    }

    public Node build(final ElementType elementType) {
        final String identifier = elementType.getIdentifier();
        if (identifier != null) {
            return ident(identifier);
        }
        return nextNode("T")
                .link(nextNode("Array"))
                .link(nextNode("<"))
                .link(build(elementType.getChild()))
                .link(nextNode(">"));
    }

    private Node nextNode(final String name) {
        lastNode += 1;
        return node(String.valueOf(lastNode)).with(Label.of(name));
    }
}
