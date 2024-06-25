package it.edu.marconiverona.classemorta;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.border.EmptyBorder;

public class RegistroDocenti extends JFrame {
    private JLabel studentNameLabel;
    private List<Student> students;
    private Image backgroundImage;
    private JComboBox<String> studentComboBox;
    private JComboBox<String> voto;
    private JComboBox<String> tipoProva;
    private JComboBox<String> materiaComboBox;
    private JButton conferma;
    private Map<String, Map<String, List<String>>> studentGrades;
    private static JTextArea gradesDisplay;
    private JTextField messaggioDOC;

    public RegistroDocenti() throws SQLException {
        try {
            backgroundImage = ImageIO.read(Main.class.getClassLoader().getResource("Filgrana_classemorta.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        students = new ArrayList<>();
        setTitle("Registro Elettronico");
        setResizable(false);
        setSize(800, 600);
        setLayout(null);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel nameLabel = new JLabel("DOCENTE: " + Login.getFullName());
        nameLabel.setBounds(300, 10, 400, 25);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        studentNameLabel = new JLabel();
        studentNameLabel.setBounds(120, 10, 200, 25);
        studentNameLabel.setForeground(Color.WHITE);
        add(nameLabel);
        add(studentNameLabel);

        JPanel centralPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // Updated to 3 rows
        centralPanel.setBackground(Color.decode("#cb0606"));
        centralPanel.setBounds(125, 50, 550, 350);
        centralPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        studentComboBox = new JComboBox<>();
        studentComboBox.setBounds(125, 50, 450, 50); // Set position and size of the JComboBox
        studentComboBox.setFont(new Font("Arial", Font.BOLD, 20));
        add(studentComboBox);

        materiaComboBox = new JComboBox<>();
        materiaComboBox.setBounds(125, 110, 450, 50); // Set position and size of the JComboBox for materie
        materiaComboBox.setFont(new Font("Arial", Font.BOLD, 20));
        add(materiaComboBox);
        loadMaterie();

        gradesDisplay = new JTextArea();
        gradesDisplay.setOpaque(false);
        studentGrades = new HashMap<>();
        caricavoti();

        gradesDisplay.setEditable(false);
        gradesDisplay.setBounds(125, 325, 600, 75);
        gradesDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        gradesDisplay.setForeground(Color.white);
        add(gradesDisplay);

        JLabel logo = new JLabel(new ImageIcon(Main.class.getClassLoader().getResource("4-3_docenti-no_bg.png")));
        add(logo);
        logo.setBounds(100, 325, 600, 300);

        voto = new JComboBox<>();
        voto.setBounds(600, 50, 75, 50); // Set position and size of the JComboBox
        voto.setFont(new Font("Arial", Font.BOLD, 20));
        add(voto);

        tipoProva = new JComboBox<>();
        tipoProva.setBounds(600, 110, 75, 50); // Set position and size of the JComboBox
        tipoProva.setFont(new Font("Arial", Font.BOLD, 20));
        add(tipoProva);
        loadTipoProva();
        loadVoti();
        loadStudentNames();

        messaggioDOC = new JTextField();
        messaggioDOC.setToolTipText("Massimo 500 caratteri");
        messaggioDOC.setBounds(125, 160, 550, 150);
        add(messaggioDOC);

        conferma = new JButton("CONFERMA");
        conferma.setBounds(325, 400, 150, 20);
        add(conferma);
        conferma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    inserisciVoto();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        add(centralPanel);

        JButton jButton4 = new JButton();
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 20));
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
        add(jButton4);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4.setForeground(new java.awt.Color(0, 0, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4.setForeground(new java.awt.Color(255, 255, 255));
            }
        });

        studentComboBox.addActionListener(e -> {
            try {
                loadStudentGrades();
                displayStudentGrades();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        materiaComboBox.addActionListener(e -> {
            try {
                loadStudentGrades();
                displayStudentGrades();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void loadStudentNames() throws SQLException {
        String query = "SELECT fullName FROM DatiLogin WHERE INSTR(email, '@studenti.com') > 0";
        PreparedStatement stmt = Main.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String studentName = rs.getString("fullName");
            studentComboBox.addItem(studentName);
            students.add(new Student(studentName)); // Adding the student to the list if needed
        }

        rs.close();
        stmt.close();
    }

    private void loadStudentGrades() throws SQLException {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        String selectedSubject = (String) materiaComboBox.getSelectedItem();

        if (selectedStudent == null || selectedSubject == null) {
            gradesDisplay.setText("Seleziona uno studente e una materia.");
            return;
        }

        String query = "SELECT voto FROM voti WHERE fullName = ? AND materia = ?";
        PreparedStatement stmt = Main.conn.prepareStatement(query);
        stmt.setString(1, selectedStudent);
        stmt.setString(2, selectedSubject);
        ResultSet rs = stmt.executeQuery();

        List<String> grades = new ArrayList<>();
        while (rs.next()) {
            grades.add(rs.getString("voto"));
        }

        if (!studentGrades.containsKey(selectedStudent)) {
            studentGrades.put(selectedStudent, new HashMap<>());
        }

        studentGrades.get(selectedStudent).put(selectedSubject, grades);

        rs.close();
        stmt.close();
    }

    private void displayStudentGrades() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        String selectedSubject = (String) materiaComboBox.getSelectedItem();

        if (selectedStudent == null || selectedSubject == null) {
            gradesDisplay.setText("Seleziona uno studente e una materia.");
            return;
        }

        Map<String, List<String>> subjectGrades = studentGrades.get(selectedStudent);
        if (subjectGrades == null || !subjectGrades.containsKey(selectedSubject)) {
            gradesDisplay.setText("Nessun voto trovato per lo studente " + selectedStudent + " nella materia " + selectedSubject);
            return;
        }

        List<String> grades = subjectGrades.get(selectedSubject);
        StringBuilder message = new StringBuilder("Voti per " + selectedStudent + " nella materia " + selectedSubject + ":\n");

        double sum = 0;
        int count = 0;
        for (String grade : grades) {
            if (grade.contains("1/2")) {
                double halfGrade = Double.parseDouble(grade.split(" ")[0]) + 0.5;
                sum += halfGrade;
                count++;
            } else {
                double fullGrade = Double.parseDouble(grade);
                sum += fullGrade;
                count++;
            }
            message.append(grade).append("  -  ");
        }

        if (count > 0) {
            double average = sum / count;
            message.append("\nMedia: ").append(String.format("%.2f", average));
        } else {
            message.append("Nessun voto disponibile.");
        }

        gradesDisplay.setText(message.toString());
    }

    public void caricavoti() throws SQLException {
        if (!studentGrades.isEmpty()) {
            String firstStudent = studentGrades.keySet().iterator().next();
            String firstSubject = studentGrades.get(firstStudent).keySet().iterator().next();
            loadStudentGrades();
            displayStudentGrades();
        }
    }

    public void loadVoti() {
        voto.addItem("3");
        voto.addItem("3 1/2");
        voto.addItem("4");
        voto.addItem("4 1/2");
        voto.addItem("5");
        voto.addItem("5 1/2");
        voto.addItem("6");
        voto.addItem("6 1/2");
        voto.addItem("7");
        voto.addItem("7 1/2");
        voto.addItem("8");
        voto.addItem("8 1/2");
        voto.addItem("9");
        voto.addItem("9 1/2");
        voto.addItem("10");
    }

    public void loadTipoProva() {
        tipoProva.addItem("Orale");
        tipoProva.addItem("Scritta");
        tipoProva.addItem("Pratica");

    }

    public void loadMaterie() {
        materiaComboBox.addItem("Italiano");
        materiaComboBox.addItem("Matematica");
        materiaComboBox.addItem("Inglese");
    }

    class Student {
        private String name;
        private int presences;
        private List<Integer> grades;

        public Student(String name) {
            this.name = name;
            this.presences = 0;
            this.grades = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void markPresence() {
            presences++;
        }

        public void addGrade(int grade) {
            grades.add(grade);
        }

        public int getPresences() {
            return presences;
        }

        public List<Integer> getGrades() {
            return grades;
        }
    }

    public void tornaIndietro() {
        InterfacciaPrima.creazione();
        this.dispose();
    }

    public void apriComunicazioni() throws SQLException {
        new Comunicazioni();
        this.dispose();
    }

    public void inserisciVoto() throws SQLException {
        String query17 = "INSERT INTO voti(materia, fullName, voto, tipoProva, nomeDocente, messaggio) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = Main.conn.prepareStatement(query17);
        String materia = materiaComboBox.getSelectedItem().toString();
        String fullName = studentComboBox.getSelectedItem().toString();
        String voto2 = voto.getSelectedItem().toString();
        String tipo = tipoProva.getSelectedItem().toString();
        String nomeDOC = Login.getFullName();
        String messDOC = messaggioDOC.getText();
        stmt.setString(1, materia);
        stmt.setString(2, fullName);
        stmt.setString(3, voto2);
        stmt.setString(4, tipo);
        stmt.setString(5, nomeDOC);
        stmt.setString(6, messDOC);
        stmt.executeUpdate();
        stmt.close();

        // Refresh grades display after inserting a new grade
        loadStudentGrades();
        displayStudentGrades();
    }
}
