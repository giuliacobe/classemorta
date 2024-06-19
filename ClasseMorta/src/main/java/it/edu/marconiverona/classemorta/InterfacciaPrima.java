package it.edu.marconiverona.classemorta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfacciaPrima {
    public static void creazione() {
        JFrame frame = new JFrame("Pannello: Seleziona opzione");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenuItem esci = new JMenuItem("ESCI");
        esci.addActionListener(e -> System.exit(0));
        menuBar.add(esci);
        frame.setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));

        Icon imagRegistro = new ImageIcon(Main.class.getClassLoader().getResource("4-3.png"));
        Icon imagTotem = new ImageIcon(Main.class.getClassLoader().getResource("TOTEM_2_LOGO_PROJCT.jpg"));
        JButton buttonTotem = new JButton(imagTotem);
        JButton buttonRegistro = new JButton(imagRegistro);

        JPanel panelTotem = new JPanel(new BorderLayout());
        panelTotem.add(buttonTotem);
        JPanel panelRegistro = new JPanel(new BorderLayout());
        panelRegistro.add(buttonRegistro);

        mainPanel.add(panelTotem);
        mainPanel.add(panelRegistro);

        frame.add(mainPanel);

        buttonTotem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new JTimbratrice();
                    }
                });
                frame.dispose();
            }
        });
        buttonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login LoginFrame = new Login();
                LoginFrame.setVisible(true);
                LoginFrame.pack();
                LoginFrame.setLocationRelativeTo(null);
                frame.dispose();
            }
        });

        frame.setVisible(true);


    }
}