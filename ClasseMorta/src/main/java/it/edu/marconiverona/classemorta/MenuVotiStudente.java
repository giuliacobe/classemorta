package it.edu.marconiverona.classemorta;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class MenuVotiStudente extends JFrame {

    // Mappa per conservare i voti degli studenti per materia
    private Map<String, Map<String, List<String>>> studentGrades;
    private JTextArea gradeDisplayArea;
    private JTextArea gradesDisplay; // Text area to display grades
    private JComboBox<String> subjectComboBox; // Combo box for selecting subject
    private Image backgroundImage;

    public MenuVotiStudente() throws SQLException {
        // Configura il frame
        setTitle("Student Grades");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            backgroundImage = ImageIO.read(Main.class.getClassLoader().getResource("Filgrana_classemorta.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gradeDisplayArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        gradesDisplay = new JTextArea();
        gradeDisplayArea.setOpaque(false);
        gradesDisplay.setOpaque(false);

        // Inizializza i voti degli studenti
        studentGrades = new HashMap<>();
        caricavoti();

        // Area to display grades
        gradeDisplayArea.setEditable(false);
        gradesDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gradeDisplayArea);
        add(gradesDisplay);
        gradesDisplay.setBounds(100, 100, 600, 350);
        add(scrollPane, BorderLayout.CENTER);
        addSubjectSelection();
        gradeDisplayArea.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        gradeDisplayArea.setForeground(Color.white);
        gradeDisplayArea.setBackground(new Color(0, 0, 0));
        gradesDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        gradesDisplay.setForeground(Color.white);

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
        String query = "SELECT fullName, voto FROM voti WHERE fullName = ? AND materia = ?";
        try (PreparedStatement stmt = Main.conn.prepareStatement(query)) {
            stmt.setString(1, Login.getFullName());
            stmt.setString(2, materia);
            try (ResultSet rs = stmt.executeQuery()) {
                Map<String, List<String>> grades = new HashMap<>();
                while (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String voto = rs.getString("voto");
                    grades.computeIfAbsent(fullName, k -> new ArrayList<>()).add(voto);
                }
                studentGrades.put(materia, grades);
            }
        }
    }

    private void displayStudentGrades(String materia) {
        Map<String, List<String>> grades = studentGrades.get(materia);
        if (grades == null || grades.isEmpty()) {
            gradesDisplay.setText("Nessun voto trovato per la materia " + materia);
            return;
        }

        StringBuilder message = new StringBuilder("Voti per la materia " + materia + ":\n");
        for (Map.Entry<String, List<String>> entry : grades.entrySet()) {
            message.append(entry.getKey()).append(": ");
            message.append(String.join(" || ", entry.getValue()));
            message.append("\n");
        }
        gradesDisplay.setText(message.toString());
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
