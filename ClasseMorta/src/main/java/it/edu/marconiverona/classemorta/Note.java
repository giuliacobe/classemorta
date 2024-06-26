package it.edu.marconiverona.classemorta;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Note extends JFrame {
    static int nRow2;
    static String tot = "";
    static int id = 1;
    private static String str = null;
    private static JTextArea label = new JTextArea();
    private Image backgroundImage;

    public Note() throws SQLException {
        // Reset static variables to prevent duplication
        tot = "";
        id = 1;
        str = null;

        // Imposta il titolo della finestra
        setTitle("Note");
        setResizable(false);
        // Imposta la dimensione della finestra
        setSize(800, 600);

        // Imposta l'operazione di chiusura
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea un pannello per contenere il messaggio

        try {
            backgroundImage = ImageIO.read(Main.class.getClassLoader().getResource("Filgrana_classemorta.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Configura la JTextArea
        label.setFont(new Font("Arial", Font.PLAIN, 20)); // Imposta il font e la dimensione del testo
        label.setEditable(false); // Rendi il JTextArea non editabile
        label.setLineWrap(true); // Abilita il ritorno a capo automatico
        label.setWrapStyleWord(true); // Rendi il ritorno a capo a livello di parola
        JScrollPane scrollPane = new JScrollPane(label); // Aggiungi uno scroll pane
        scrollPane.setPreferredSize(new Dimension(600, 400)); // Imposta la dimensione preferita dello scroll pane
        JLabel logo = new JLabel(new ImageIcon(Main.class.getClassLoader().getResource("logo2_no_bg.png")));
        add(logo);
        logo.setBounds(85, 300, 600, 300);
        // Aggiungi lo scroll pane al pannello
        panel.add(scrollPane);
        crea();

        // Aggiungi il pannello al frame
        add(panel, BorderLayout.CENTER);

        JButton jButton4 = new JButton("Home");
        jButton4.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jButton4.setForeground(new Color(0, 0, 0));
        jButton4.setOpaque(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    tornaIndietro();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        add(jButton4, BorderLayout.SOUTH);

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
        String comun = "";
        String query10 = "SELECT comunicazione FROM ( SELECT comunicazione, ROW_NUMBER() OVER (ORDER BY comunicazione) AS rn FROM Comun ) WHERE rn = ?";
        try (PreparedStatement stmt = Main.conn.prepareStatement(query10)) {
            stmt.setInt(1, id);
            id += 1;
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comun = rs.getString("Note");
                }
            }
        }
        return comun;
    }

    public static void createString() throws SQLException {
        str = "Note: " + addText() + "\n";
        tot = "\n" + tot + str + "\n";
        label.setText(tot);
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

