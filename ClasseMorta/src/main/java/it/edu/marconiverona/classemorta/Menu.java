package it.edu.marconiverona.classemorta;

import java.awt.EventQueue;
import java.awt.Frame;

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
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon ico = new ImageIcon(Main.class.getClassLoader().getResource("Designer.png"));
        JLabel label = new JLabel(ico);
        frame.add(label);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
