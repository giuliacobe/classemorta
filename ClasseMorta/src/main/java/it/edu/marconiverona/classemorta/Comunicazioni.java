package it.edu.marconiverona.classemorta;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comunicazioni extends JFrame {
    static int nRow2;
    static String tot = "";
    static int id = 1;
    private static String str = null;
    private static JTextArea label = new JTextArea();

    public Comunicazioni() throws SQLException {
        // Imposta il titolo della finestra
        setTitle("Comunicazioni");

        // Imposta la dimensione della finestra
        setSize(800, 600);

        // Imposta l'operazione di chiusura
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea un pannello per contenere il messaggio
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE); // Imposta lo sfondo bianco

        // Crea una label con il messaggio di comunicazione
        label.setFont(new Font("Arial", Font.PLAIN, 16)); // Imposta il font e la dimensione del testo

        // Aggiungi la label al pannello
        panel.add(label);

        crea();

        // Aggiungi il pannello al frame
        add(panel, BorderLayout.CENTER);

        JButton jButton4 = new JButton();
        jButton4.setFont(new Font("Segoe UI", 0, 14));
        jButton4.setText("Home");
        jButton4.setForeground(new Color(0, 0, 0));
        jButton4.setOpaque(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setBorderPainted(false);
        jButton4.setDefaultCapable(false);
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    tornaIndietro();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        add(jButton4, BorderLayout.NORTH);

        // Rendi la finestra visibile
        setVisible(true);
    }

    public void tornaIndietro() throws SQLException {
        RegistroElettronicoApp app = new RegistroElettronicoApp();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        this.dispose();
    }

    public static String addText() throws SQLException {
        String comun = null;
        String query10 = "SELECT comunicazione FROM ( SELECT comunicazione, ROW_NUMBER() OVER (ORDER BY comunicazione) AS rn FROM Comun ) WHERE rn = " + id;
        id += 1;
        try (PreparedStatement stmt = Main.conn.prepareStatement(query10)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comun = rs.getString("comunicazione");
                }
            }
        }
        return comun;
    }

    public static void createString() throws SQLException {
        int nRow;
        String linee = "SELECT COUNT(comunicazione) AS numero_di_righe FROM Comun";
        try (PreparedStatement stmt = Main.conn.prepareStatement(linee)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nRow = rs.getInt("numero_di_righe");
                    str = "Comunicazione: " + addText() + "\n";
                    tot = "\n" + tot + str + "\n";
                    label.setText(tot);
                }
            }
        }
    }

    public void crea() throws SQLException {
        String linee2 = "SELECT COUNT(comunicazione) AS numero_di_righe FROM Comun";
        try (PreparedStatement stmt = Main.conn.prepareStatement(linee2)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nRow2 = rs.getInt("numero_di_righe");
                    for (int k = 0; k < nRow2; k++) {
                        createString();
                    }
                }
            }
        }
    }
}

