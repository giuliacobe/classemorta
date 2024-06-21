package it.edu.marconiverona.classemorta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistroPreside extends JFrame implements ActionListener {
    public static JTextArea listaScrollabile;
    private JLabel titolo;
    private JButton buttonInvia;

    public RegistroPreside() {
        setTitle("Registro Preside");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 242, 235));

        // Caricamento e ridimensionamento dell'immagine con alta qualità usando imgscalr
        titolo = new JLabel("Registro Preside");
        titolo.setFont(new Font("Arial", Font.BOLD, 15));
        titolo.setBounds(225, 20, 150, 60); // Leggera riduzione delle dimensioni
        buttonInvia = new JButton("INVIA");
        buttonInvia.setBounds(100, 400, 400, 50);
        buttonInvia.addActionListener(this);

        listaScrollabile = new JTextArea();
        listaScrollabile.setBounds(100, 100, 400, 300);

        add(titolo); // Aggiunge titolo al frame
        add(buttonInvia);
        add(listaScrollabile);

        JButton jButton4 = new JButton();
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton4.setText("Home");
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
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
        add(jButton4);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4.setForeground(new java.awt.Color(255, 0, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4.setForeground(new java.awt.Color(0, 0, 0));
            }
        });


        setVisible(true);
    }
    public void tornaIndietro() {
        InterfacciaPrima.creazione();
        this.dispose();
    }

    public static String getComun(){
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

