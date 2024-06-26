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

public class Comunicazioni extends JFrame {
    static int nRow2;
    static int id = 1;
    private static JPanel comunicazioniPanel;
    private Image backgroundImage;

    public Comunicazioni() throws SQLException {
        // Reset static variables to prevent duplication
        id = 1;

        // Set the title of the window
        setTitle("Comunicazioni");
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

        // Create a panel to hold the communications
        comunicazioniPanel = new JPanel();
        comunicazioniPanel.setLayout(new BoxLayout(comunicazioniPanel, BoxLayout.Y_AXIS));
        comunicazioniPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(comunicazioniPanel);
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
        String comun = "";
        String query10 = "SELECT comunicazione FROM ( " +
                "SELECT comunicazione, ROW_NUMBER() OVER (ORDER BY comunicazione) AS rn FROM Comun ) WHERE rn = ?";
        try (PreparedStatement stmt = Main.conn.prepareStatement(query10)) {
            stmt.setInt(1, id);
            id += 1;
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comun = rs.getString("comunicazione");
                }
            }
        }
        return comun;
    }

    public static void createString() throws SQLException {
        String comunContent = "Comunicazione: " + addText();

        JPanel comunPanel = new JPanel();
        comunPanel.setLayout(new BorderLayout());
        comunPanel.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 10, 0, 10, Color.decode("#cb0606")), // Add red borders to the left and right sides
                new LineBorder(Color.BLACK, 1, true) // Add black border to the rest of the sides
        ));
        comunPanel.setBackground(Color.WHITE); // Set background to white

        JTextArea comunArea = new JTextArea(comunContent);
        comunArea.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font and text size
        comunArea.setEditable(false); // Make the JTextArea non-editable
        comunArea.setLineWrap(true); // Enable line wrap
        comunArea.setWrapStyleWord(true); // Wrap lines at word boundaries
        comunArea.setOpaque(false);

        comunPanel.add(comunArea, BorderLayout.CENTER);
        comunicazioniPanel.add(comunPanel);
        comunicazioniPanel.add(Box.createVerticalStrut(10)); // Add spacing between communications
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
