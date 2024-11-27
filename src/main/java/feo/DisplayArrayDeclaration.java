package feo;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import javax.swing.*;
import java.awt.*;


public class DisplayArrayDeclaration {
    static class ImagePanel extends JPanel {
        private final Image image;

        public ImagePanel(final Image image) {
            this.image = image;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }
    private static final int PADDING = 40;

    public static Frame display(final ArrayDeclaration arrayDeclaration) {
        Graphviz.noHeadless();
        final Image image = Graphviz
                .fromGraph(new GraphBuilder().build(arrayDeclaration))
                .render(Format.PNG)
                .toImage();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new ImagePanel(image);
        f.setPreferredSize(
                new Dimension(
                        image.getWidth(null),
                        image.getHeight(null) + PADDING));
        f.add(panel);
        f.pack();
        f.setVisible(true);
        return f;
    }
}
