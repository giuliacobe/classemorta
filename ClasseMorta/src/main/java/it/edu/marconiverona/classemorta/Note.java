package it.edu.marconiverona.classemorta;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Note extends JFrame {
    static int nRow2;
    static int id = 1;
    private static JPanel notesPanel;
    private Image backgroundImage;

    public Note() throws SQLException {
        // Reset static variables to prevent duplication
        id = 1;

        // Set the title of the window
        setTitle("Note");
        setResizable(false);
        // Set the size of the window
        setSize(800, 600);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to contain the message
        try {
            backgroundImage = ImageIO.read(Main.class.getClassLoader().getResource("Filgrana_classemorta.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Create a panel to hold the notes
        notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS));
        notesPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(notesPanel);
        scrollPane.setPreferredSize(new Dimension(600, 400)); // Set the preferred size of the scroll pane
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JLabel logo = new JLabel(new ImageIcon(Main.class.getClassLoader().getResource("logo2_no_bg.png")));
        mainPanel.add(logo, BorderLayout.NORTH);

        // Add the scroll pane to the main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        crea();

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        JButton jButton4 = new JButton("Home");
        jButton4.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jButton4.setForeground(new Color(0, 0, 0));
        jButton4.setOpaque(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(evt -> {
            try {
                tornaIndietro();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        add(jButton4, BorderLayout.SOUTH);

        // Make the window visible
        setVisible(true);
    }

    public void tornaIndietro() throws SQLException {
        RegistroElettronicoApp app = new RegistroElettronicoApp();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        this.dispose();
    }

    public static String addText() throws SQLException {
        String noteDetails = "";
        String query = "SELECT tipoNota, nomeDocente, messaggio FROM ( " +
                "SELECT tipoNota, nomeDocente, messaggio, ROW_NUMBER() OVER (ORDER BY tipoNota) AS rn " +
                "FROM note WHERE fullName = ? ) WHERE rn = ?";
        try (PreparedStatement stmt = Main.conn.prepareStatement(query)) {
            stmt.setString(1, Login.getFullName());
            stmt.setInt(2, id);
            id += 1;
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipoNota = rs.getString("tipoNota");
                    String docente = rs.getString("nomeDocente");
                    String messaggio = rs.getString("messaggio");
                    noteDetails = "Tipo di Nota: " + tipoNota + "\nDocente: " + docente + "\nMessaggio: " + messaggio + "\n";
                }
            }
        }
        return noteDetails;
    }

    public static void createString() throws SQLException {
        String noteContent = addText();

        JPanel notePanel = new JPanel();
        notePanel.setLayout(new BorderLayout());
        notePanel.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 10, 0, 10, Color.decode("#cb0606")), // Add red borders to the left and right sides
                new LineBorder(Color.BLACK, 1, true) // Add black border to the rest of the sides
        ));
        notePanel.setBackground(Color.WHITE); // Set background to white

        JTextArea noteArea = new JTextArea(noteContent);
        noteArea.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font and text size
        noteArea.setEditable(false); // Make the JTextArea non-editable
        noteArea.setLineWrap(true); // Enable line wrap
        noteArea.setWrapStyleWord(true); // Wrap lines at word boundaries
        noteArea.setOpaque(false);

        notePanel.add(noteArea, BorderLayout.CENTER);
        notesPanel.add(notePanel);
        notesPanel.add(Box.createVerticalStrut(10)); // Add spacing between notes
    }

    public void crea() throws SQLException {
        String linee2 = "SELECT COUNT(*) AS numero_di_righe FROM note WHERE fullName = ?";
        try (PreparedStatement stmt = Main.conn.prepareStatement(linee2)) {
            stmt.setString(1, Login.getFullName());
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
