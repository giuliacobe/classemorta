package it.edu.marconiverona.classemorta;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Desktop;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Menu implements Runnable {
    private JFrame frame;

    public void run() {
        showGui();
    }

    private void showGui() {
        frame = new JFrame("Click Me");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon ico = new ImageIcon(Main.class.getClassLoader().getResource("Designer.png"));
        JLabel label = new JLabel(ico);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.mcdonalds.it/"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(label);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(800, 600);
    }
}
