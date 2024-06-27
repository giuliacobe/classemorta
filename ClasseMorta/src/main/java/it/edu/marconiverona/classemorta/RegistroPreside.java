package it.edu.marconiverona.classemorta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroPreside extends JFrame implements ActionListener {
    public static JTextField listaScrollabile;
    private JLabel titolo;
    private JButton buttonInvia;
    private BufferedImage backgroundImage;

    public RegistroPreside() {
        try {
            backgroundImage = ImageIO.read(Main.class.getClassLoader().getResource("Filgrana_classemorta.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Registro Preside");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        titolo = new JLabel("Registro Preside");
        titolo.setFont(new Font("Arial", Font.BOLD, 17));
        titolo.setForeground(Color.white);
        titolo.setBounds(225, 20, 150, 60);
        buttonInvia = new JButton("INVIA");
        buttonInvia.setBounds(100, 400, 400, 50);
        buttonInvia.addActionListener(this);

        listaScrollabile = new JTextField();
        listaScrollabile.setBounds(100, 100, 400, 300);

        panel.add(titolo);
        panel.add(buttonInvia);
        panel.add(listaScrollabile);

        JButton jButton4 = new JButton();
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton4.setText("Home");
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setOpaque(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setBorderPainted(false);
        jButton4.setDefaultCapable(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tornaIndietro();
            }
        });
        jButton4.setBackground(null);
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton4.setBounds(-20, -5, 93, 36);
        panel.add(jButton4);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4.setForeground(new java.awt.Color(0, 0, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4.setForeground(new java.awt.Color(255, 255, 255));
            }
        });

        setContentPane(panel);
        setVisible(true);
    }

    public void tornaIndietro() {
        InterfacciaPrima.creazione();
        this.dispose();
    }

    public static String getComun() {
        return listaScrollabile.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            addComunDB();
            Comunicazioni.addText();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void addComunDB() throws SQLException {
        String insertQuery = "INSERT INTO Comun(comunicazione) VALUES(?)";
        PreparedStatement pstmt = Main.conn.prepareStatement(insertQuery);
        pstmt.setString(1, getComun());
        pstmt.executeUpdate();
    }
}
