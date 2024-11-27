package feo;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class Main {
    private static final int WIDTH = 500;

    public static void main(String[] args) {
        Graphviz.noHeadless();
        final Image image;
        try {
            var in = new ByteArrayInputStream("var a: Array<Array<Array<b>>>;".getBytes(StandardCharsets.UTF_8));
//            final ArrayDeclaration arrayDeclaration = new Parser().parse(System.in);
            final ArrayDeclaration arrayDeclaration = new Parser().parse(in);
            DisplayArrayDeclaration.display(arrayDeclaration);
        } catch (final ParseException e) {
            System.err.println("During parsing an error occurred: " + e);
            return;
        }
//        SwingUtilities.invokeLater(() -> {
//        });
    }
}