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

    private Map<String, Map<String, List<String>>> studentGrades;
    private JTextArea gradeDisplayArea;
    private JTextArea gradesDisplay;
    private JComboBox<String> subjectComboBox;
    private Image backgroundImage;

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

        JScrollPane gradeDisplayScrollPane = new JScrollPane(gradesDisplay);
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


        addSubjectSelection();

        gradeDisplayArea.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        gradeDisplayArea.setForeground(Color.WHITE);
        gradeDisplayArea.setBackground(new Color(0, 0, 0));
        gradesDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        gradesDisplay.setForeground(Color.WHITE);
        gradesDisplay.setBackground(new Color(0, 0, 0)); // Setting the background for better visibility

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
        Map<String, List<String>> grades = studentGrades.get(materia);
        if (grades == null || grades.isEmpty()) {
            gradesDisplay.setText("Nessun voto trovato per la materia " + materia);
            return;
        }

        StringBuilder message = new StringBuilder("Voti per la materia " + materia + ":\n");

        for (Map.Entry<String, List<String>> entry : grades.entrySet()) {
            List<String> studentGrades = entry.getValue();
            message.append("\n");
            double somma = 0;
            int count = 0;
            for (String gradeWithTipoAndMessaggio : studentGrades) {
                String[] parts = gradeWithTipoAndMessaggio.split(" \\(");
                String grade = parts[0];
                String tipoProvaAndMessaggio = parts[1].replace(")", "");

                if (grade.contains("1/2")) {
                    double votoMezzo = Double.parseDouble(String.valueOf(grade.charAt(0)));
                    votoMezzo += Double.parseDouble("0.5");
                    somma += votoMezzo;
                    count++;
                } else {
                    int voto = Integer.parseInt(grade);
                    somma += voto;
                    count++;
                }
            }
            double media = somma / count;
            message.append("\n");
            message.append(String.join("\n", studentGrades));
            message.append("\n");
            message.append("\n");
            message.append("Media: ").append(String.format("%.2f", media));
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
