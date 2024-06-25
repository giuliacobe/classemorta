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
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class RegistroElettronicoApp extends JFrame {
    private JLabel assenze;
    private JLabel numeroAssenze;
    private JLabel ritardi;
    private JLabel numeroRitardi;
    private JLabel presenze;
    private JLabel numeroPresenze;
    private JLabel uscite;
    private JLabel numeroUscite;
    private JLabel studentNameLabel;
    private JList<String> centralList1, centralList2, centralList3, centralList4;
    private JComboBox<String> studentComboBox;
    private JButton markPresenceButton;
    private JButton comunicazioni;
    private List<Student> students;
    private Image backgroundImage;

    public RegistroElettronicoApp() throws SQLException {
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

        JLabel nameLabel = new JLabel("NOME STUDENTE:" + Login.getFullName());
        nameLabel.setBounds(300, 10, 400, 25);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        studentNameLabel = new JLabel();
        studentNameLabel.setBounds(120, 10, 200, 25);
        studentNameLabel.setForeground(Color.WHITE);
        add(nameLabel);
        add(studentNameLabel);


        JPanel centralPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, 10px gap
        centralPanel.setBackground(Color.decode("#cb0606"));
        centralPanel.setBounds(125, 50, 550, 350);
        centralPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(centralPanel);


        centralList1 = createCentralList();
        centralList2 = createCentralList();
        centralList3 = createCentralList();
        centralList4 = createCentralList();

        //assenze

        String query = "SELECT assenze FROM DatiLogin WHERE fullName = ?";
        String nAssenze = null;
        try (PreparedStatement stmt = Main.conn.prepareStatement(query)) {
            stmt.setString(1, Login.getFullName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nAssenze = rs.getString("assenze");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        numeroAssenze = new JLabel(nAssenze);
        numeroAssenze.setBounds(120, -100, 350, 350);
        numeroAssenze.setFont(new Font("Arial", Font.BOLD, 50));
        numeroAssenze.setForeground(Color.WHITE);

        assenze = new JLabel("Assenze");
        assenze.setBounds(100, 75, 75, 75);
        assenze.setFont(new Font("Arial", Font.BOLD, 15));
        assenze.setForeground(Color.WHITE);
        centralList1.add(numeroAssenze);
        centralList1.add(assenze);

        //ritardi

        String query4 = "SELECT ritardi FROM DatiLogin WHERE fullName = ?";
        String nRitardi = null;
        try (PreparedStatement stmt = Main.conn.prepareStatement(query4)) {
            stmt.setString(1, Login.getFullName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nRitardi = rs.getString("ritardi");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        numeroRitardi = new JLabel(nRitardi);
        numeroRitardi.setBounds(112, -100, 350, 350);
        numeroRitardi.setFont(new Font("Arial", Font.BOLD, 50));
        numeroRitardi.setForeground(Color.WHITE);

        ritardi = new JLabel("Ritardi");
        ritardi.setBounds(112, 75, 75, 75);
        ritardi.setFont(new Font("Arial", Font.BOLD, 15));
        ritardi.setForeground(Color.WHITE);
        centralList2.add(numeroRitardi);
        centralList2.add(ritardi);

        //presenze

        String query2 = "SELECT presenze FROM DatiLogin WHERE fullName = ?";
        String nPresenze = null;
        try (PreparedStatement stmt = Main.conn.prepareStatement(query2)) {
            stmt.setString(1, Login.getFullName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nPresenze = rs.getString("presenze");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        numeroPresenze = new JLabel(nPresenze);
        numeroPresenze.setBounds(112, -100, 350, 350);
        numeroPresenze.setFont(new Font("Arial", Font.BOLD, 50));
        numeroPresenze.setForeground(Color.WHITE);

        presenze = new JLabel("Presenze");
        presenze.setBounds(112, 75, 75, 75);
        presenze.setFont(new Font("Arial", Font.BOLD, 15));
        presenze.setForeground(Color.WHITE);
        centralList3.add(numeroPresenze);
        centralList3.add(presenze);

        //Uscite

        String query3 = "SELECT uscite FROM DatiLogin WHERE fullName = ?";
        String nUscite = null;
        try (PreparedStatement stmt = Main.conn.prepareStatement(query3)) {
            stmt.setString(1, Login.getFullName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nUscite = rs.getString("uscite");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        numeroUscite = new JLabel(nUscite);
        numeroUscite.setBounds(112, -100, 350, 350);
        numeroUscite.setFont(new Font("Arial", Font.BOLD, 50));
        numeroUscite.setForeground(Color.WHITE);

        uscite = new JLabel("Uscite");
        uscite.setBounds(112, 75, 75, 75);
        uscite.setFont(new Font("Arial", Font.BOLD, 15));
        uscite.setForeground(Color.WHITE);
        centralList4.add(numeroUscite);
        centralList4.add(uscite);



        centralPanel.add(new JScrollPane(centralList1));
        centralPanel.add(new JScrollPane(centralList2));
        centralPanel.add(new JScrollPane(centralList3));
        centralPanel.add(new JScrollPane(centralList4));

        // Pannello inferiore per registrare presenze e voti
        JButton voti = new JButton("VOTI");
        voti.setBounds(150, 420, 200, 25); // Imposta la posizione e la dimensione del pulsante
        voti.setEnabled(true); // Rende il pulsante cliccabile
        voti.setForeground(Color.WHITE); // Imposta il colore del testo del pulsante
        voti.setBackground(Color.decode("#d15c5c")); // Imposta il colore di sfondo del pulsante
        add(voti);

        voti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aprimenuvoti();
            }
        });


        // BOX cose da giustificare
        studentComboBox = new JComboBox<>();
        studentComboBox.setBounds(450, 450, 200, 25); // Imposta la posizione e la dimensione del JComboBox
        add(studentComboBox);

        String query10 = "SELECT assenzeDG, usciteDG, ritardiDG FROM DatiLogin WHERE fullName = ?";
        PreparedStatement stmt = Main.conn.prepareStatement(query10);
        stmt.setString(1, Login.getFullName());
        ResultSet rs = stmt.executeQuery();
        int asDG = 0;
        int usDG = 0;
        int ritDG = 0;
        if (rs.next()) {
            asDG = Integer.parseInt(rs.getString("assenzeDG"));
            usDG = Integer.parseInt(rs.getString("usciteDG"));
            ritDG = Integer.parseInt(rs.getString("ritardiDG"));
        }

        for (int i = 0; i < asDG; i++) {
            studentComboBox.addItem("Assenza " + i + " Da Giustificare");
        }
        for (int i = 0; i < usDG; i++) {
            studentComboBox.addItem("Uscita " + i + " Da Giustificare");
        }
        for (int i = 0; i < ritDG; i++) {
            studentComboBox.addItem("Ritardo " + i + " Da Giustificare");
        }


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

        markPresenceButton = new JButton("GIUSTIFICA EVENTO");
        markPresenceButton.setBounds(450, 420, 200, 25); // Imposta la posizione e la dimensione del pulsante
        markPresenceButton.setForeground(Color.WHITE); // Imposta il colore del testo del pulsante
        markPresenceButton.setBackground(Color.decode("#d15c5c")); // Imposta il colore di sfondo del pulsante
        add(markPresenceButton);
        markPresenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String obj = (String) studentComboBox.getSelectedItem();
                studentComboBox.removeItem(obj);
                String ass = "Assenza";
                String usc = "Uscita";
                String rit = "Ritardo";
                if (obj.startsWith(ass)) {
                    String query12 = "UPDATE DatiLogin SET assenzeDG = assenzeDG - 1 WHERE fullName = ?";
                    PreparedStatement stmt = null;
                    try {
                        stmt = Main.conn.prepareStatement(query12);
                        stmt.setString(1, Login.getFullName());
                        stmt.executeQuery();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (obj.startsWith(usc)) {
                    String query13 = "UPDATE DatiLogin SET usciteDG = usciteDG - 1 WHERE fullName = ?";
                    PreparedStatement stmt = null;
                    try {
                        stmt = Main.conn.prepareStatement(query13);
                        stmt.setString(1, Login.getFullName());
                        stmt.executeQuery();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (obj.startsWith(rit)) {
                    String query14 = "UPDATE DatiLogin SET ritardiDG = ritardiDG - 1 WHERE fullName = ?";
                    PreparedStatement stmt = null;
                    try {
                        stmt = Main.conn.prepareStatement(query14);
                        stmt.setString(1, Login.getFullName());
                        stmt.executeQuery();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

        comunicazioni = new JButton("COMUNICAZIONI");
        comunicazioni.setBounds(150, 450, 200, 25); // Imposta la posizione e la dimensione del pulsante
        comunicazioni.setForeground(Color.WHITE); // Imposta il colore del testo del pulsante
        comunicazioni.setBackground(Color.decode("#d15c5c")); // Imposta il colore di sfondo del pulsante
        add(comunicazioni);
        comunicazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    apriComunicazioni();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private JList<String> createCentralList() {
        JList<String> list = new JList<>(new DefaultListModel<>());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBackground(Color.decode("#d15c5c"));
        return list;
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

    public void aprimenuvoti(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MenuVotiStudente().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.dispose();
    }

}