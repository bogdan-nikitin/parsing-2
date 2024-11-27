package feo;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

class ImagePanel extends JPanel {
    private final Image image;

    public ImagePanel(final Image image) {
        this.image = image;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}

public class Main {
    private static final int WIDTH = 500;

    public static void main(String[] args) {
        Graphviz.noHeadless();
        final Image image;
        try {
            var in = new ByteArrayInputStream("var a: Array<b>;".getBytes(StandardCharsets.UTF_8));
//            final ArrayDeclaration arrayDeclaration = new Parser().parse(System.in);
            final ArrayDeclaration arrayDeclaration = new Parser().parse(in);
            image = Graphviz.fromGraph(GraphBuilder.build(arrayDeclaration)).width(WIDTH).render(Format.PNG).toImage();
        } catch (final ParseException e) {
            System.err.println("During parsing an error occurred: " + e);
            return;
        }
        SwingUtilities.invokeLater(() -> {
            Frame f = new JFrame();
            JPanel panel = new ImagePanel(image);
            f.add(panel);
            f.pack();
            f.setVisible(true);
        });
    }
}