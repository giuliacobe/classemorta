/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.edu.marconiverona.classemorta;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class RegistroElettronicoApp extends JFrame {
    private JLabel studentNameLabel;
    private JList<String> centralList;
    private JComboBox<String> studentComboBox;
    private JButton markPresenceButton;
    private List<Student> students;

    public RegistroElettronicoApp() {
        students = new ArrayList<>();
        setTitle("Registro Elettronico");
        getContentPane().setBackground(Color.decode("#cb0606"));
        setResizable(false);
        setSize(800, 600);
        setLayout(null);

        JLabel nameLabel = new JLabel("NOME STUDENTE:" + Login.getFullName());
        nameLabel.setBounds(300, 10, 250, 25);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        studentNameLabel = new JLabel();
        studentNameLabel.setBounds(120, 10, 200, 25);
        studentNameLabel.setForeground(Color.WHITE);
        add(nameLabel);
        add(studentNameLabel);

        centralList = new JList<>(new DefaultListModel<>());
        centralList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Selezione singola nella lista
        centralList.setBackground(Color.decode("#d15c5c")); // Imposta il colore di sfondo della lista
        JScrollPane scrollPane = new JScrollPane(centralList); // Avvolge la lista in un JScrollPane
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0)); // Rimuove il bordo del JScrollPane
        scrollPane.setBounds(125, 50, 550, 350); // Dimensione e posizione del JScrollPane
        add(scrollPane);

        // Pannello inferiore per registrare presenze e voti
        JButton storico = new JButton("STORICO EVENTI");
        storico.setBounds(150, 420, 200, 25); // Imposta la posizione e la dimensione del pulsante
        storico.setEnabled(true); // Rende il pulsante cliccabile
        storico.setForeground(Color.WHITE); // Imposta il colore del testo del pulsante
        storico.setBackground(Color.decode("#d15c5c")); // Imposta il colore di sfondo del pulsante
        add(storico);

        studentComboBox = new JComboBox<>();
        studentComboBox.setBounds(150, 450, 200, 25); // Imposta la posizione e la dimensione del JComboBox
        add(studentComboBox);

        markPresenceButton = new JButton("GIUSTIFICA EVENTO");
        markPresenceButton.setBounds(450, 420, 200, 25); // Imposta la posizione e la dimensione del pulsante
        markPresenceButton.setForeground(Color.WHITE); // Imposta il colore del testo del pulsante
        markPresenceButton.setBackground(Color.decode("#d15c5c")); // Imposta il colore di sfondo del pulsante
        add(markPresenceButton);
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
}