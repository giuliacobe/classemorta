package it.edu.marconiverona.classemorta;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class MenuVotiStudente extends JFrame {

    private Map<String, Map<String, List<String>>> studentGrades;
    private JPanel gradesDisplayPanel;
    private JComboBox<String> subjectComboBox;
    private Image backgroundImage;
    private static JLabel averageLabel = new JLabel("Media: ");

    public MenuVotiStudente() throws SQLException {
        // Configura il frame
        setTitle("Student Grades");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        try {
            backgroundImage = ImageIO.read(Main.class.getClassLoader().getResource("Filgrana_classemorta.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gradesDisplayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        gradesDisplayPanel.setOpaque(false);

        // Set the layout to GridLayout with 4 rows and 1 column initially (will adjust later)
        gradesDisplayPanel.setLayout(new GridLayout(0, 4, 10, 10)); // 4 columns, dynamic rows, 10px gaps

        // Inizializza i voti degli studenti
        studentGrades = new HashMap<>();

        JScrollPane gradeDisplayScrollPane = new JScrollPane(gradesDisplayPanel);
        gradeDisplayScrollPane.setOpaque(false);
        gradeDisplayScrollPane.getViewport().setOpaque(false);
        gradeDisplayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Create a custom panel with background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(gradeDisplayScrollPane, BorderLayout.CENTER);

        add(backgroundPanel, BorderLayout.CENTER); // Ensure the custom panel is added to the center
        caricavoti();
        addSubjectSelection();

        // Panel for average grade
        JPanel averagePanel = new JPanel();
        averagePanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background
        averagePanel.setPreferredSize(new Dimension(200, 200));
        averageLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        averageLabel.setForeground(Color.WHITE);
        averagePanel.add(averageLabel);
        backgroundPanel.add(averagePanel, BorderLayout.WEST);

        // Home button
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Segoe UI", 0, 20));
        homeButton.setOpaque(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setDefaultCapable(false);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    tornaIndietro();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        homeButton.setBounds(-20, -5, 93, 36);
        add(homeButton, BorderLayout.SOUTH);
        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeButton.setForeground(new Color(0, 0, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeButton.setForeground(new Color(0, 0, 0));
            }
        });
    }

    private void loadStudentGrades(String materia) throws SQLException {
        String query = "SELECT fullName, voto, tipoProva, messaggio, nomeDocente FROM voti WHERE fullName = ? AND materia = ?";
        try (PreparedStatement stmt = Main.conn.prepareStatement(query)) {
            stmt.setString(1, Login.getFullName());
            stmt.setString(2, materia);
            try (ResultSet rs = stmt.executeQuery()) {
                Map<String, List<String>> grades = new HashMap<>();
                while (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String voto = rs.getString("voto");
                    String tipoProva = rs.getString("tipoProva");  // Fetch the test type
                    String messaggio = rs.getString("messaggio"); // Fetch the teacher's message
                    String nomeDOC = rs.getString("nomeDocente");
                    grades.computeIfAbsent(fullName, k -> new ArrayList<>()).add(voto + " (" + tipoProva + ")" + "\n" + "Messaggio: " + messaggio + "\n" + "Docente: " + nomeDOC + "\n"
                            + "-----------------------------------------------------------------------------------------------------------------------");  // Include the test type and teacher's message
                }
                studentGrades.put(materia, grades);
            }
        }
    }

    private void displayStudentGrades(String materia) {
        gradesDisplayPanel.removeAll();
        Map<String, List<String>> grades = studentGrades.get(materia);
        if (grades == null || grades.isEmpty()) {
            gradesDisplayPanel.add(new JLabel("Nessun voto trovato per la materia " + materia));
            gradesDisplayPanel.revalidate();
            gradesDisplayPanel.repaint();
            return;
        }

        StringBuilder message = new StringBuilder("Voti per la materia " + materia + ":\n");

        double somma = 0;
        int count = 0;

        for (Map.Entry<String, List<String>> entry : grades.entrySet()) {
            List<String> studentGrades = entry.getValue();
            for (String gradeWithTipoAndMessaggio : studentGrades) {
                String[] parts = gradeWithTipoAndMessaggio.split(" \\(");
                String grade = parts[0].trim();
                String tipoProvaAndMessaggio = parts[1].replace(")", "");

                double voto = 0.0;
                if (grade.contains("1/2")) {
                    String[] gradeParts = grade.split(" 1/2");
                    voto = Double.parseDouble(gradeParts[0]) + 0.5;
                } else {
                    voto = Double.parseDouble(grade);
                }

                somma += voto;
                count++;

                JLabel gradeLabel = new JLabel(grade);
                gradeLabel.setForeground(Color.WHITE);
                gradeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 25));
                gradeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                if (voto <= 4.5) {
                    gradeLabel.setBackground(new Color(255, 0, 0)); // Red for low grades
                } else if (voto > 4.5 && voto < 6) {
                    gradeLabel.setBackground(new Color(255, 165, 0)); // Yellow for medium grades
                } else if (voto >= 6) {
                    gradeLabel.setBackground(new Color(0, 128, 0)); // Green for high grades
                }

                gradeLabel.setOpaque(true);
                gradeLabel.setPreferredSize(new Dimension(80, 80)); // Imposta dimensioni quadrate
                gradeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centro il testo

                gradeLabel.setBorder(new LineBorder(Color.WHITE));

                String finalGradeWithTipoAndMessaggio = gradeWithTipoAndMessaggio;
                gradeLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showGradeDetailsDialog(finalGradeWithTipoAndMessaggio);
                    }
                });

                gradesDisplayPanel.add(gradeLabel);
            }
            message.append("\n");
            message.append(String.join("\n", studentGrades));
            message.append("\n");
            message.append("\n");
        }

        double media = somma / count;
        averageLabel.setText("Media: " + String.format("%.2f", media)); // Update the average label
        gradesDisplayPanel.revalidate();
        gradesDisplayPanel.repaint();
    }

    private void showGradeDetailsDialog(String gradeDetails) {
        JOptionPane.showMessageDialog(this, gradeDetails, "Dettagli del Voto", JOptionPane.INFORMATION_MESSAGE);
    }

    public void tornaIndietro() throws SQLException {
        RegistroElettronicoApp app = new RegistroElettronicoApp();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        this.dispose();
    }

    public void caricavoti() throws SQLException {
        loadAllSubjects();
        if (!studentGrades.isEmpty()) {
            String firstSubject = studentGrades.keySet().iterator().next();
            loadStudentGrades(firstSubject);
            displayStudentGrades(firstSubject);
        }
    }

    private void loadAllSubjects() throws SQLException {
        String query = "SELECT DISTINCT materia FROM voti WHERE fullName = ?";
        try (PreparedStatement stmt = Main.conn.prepareStatement(query)) {
            stmt.setString(1, Login.getFullName());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String materia = rs.getString("materia");
                    studentGrades.put(materia, new HashMap<>());
                }
            }
        }
    }

    private void addSubjectSelection() {
        subjectComboBox = new JComboBox<>();
        for (String materia : studentGrades.keySet()) {
            subjectComboBox.addItem(materia);
        }
        subjectComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedSubject = (String) subjectComboBox.getSelectedItem();
                try {
                    loadStudentGrades(selectedSubject);
                    displayStudentGrades(selectedSubject);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        JPanel selectionPanel = new JPanel();
        selectionPanel.setForeground(Color.white);
        JLabel scritta = new JLabel("Seleziona la materia: ");
        scritta.setForeground(Color.white);
        selectionPanel.setBackground(Color.decode("#A90B0B"));
        selectionPanel.add(scritta);
        selectionPanel.add(subjectComboBox);

        add(selectionPanel, BorderLayout.NORTH);
    }
}
