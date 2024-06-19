/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.edu.marconiverona.classemorta;

import javax.swing.*;
import java.awt.*;
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

        // Create a panel with GridLayout to hold four lists
        JPanel centralPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, 10px gap
        centralPanel.setBackground(Color.decode("#cb0606"));
        centralPanel.setBounds(125, 50, 550, 350);
        centralPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(centralPanel);

        // Create and configure the four JLists
        centralList1 = createCentralList();
        centralList2 = createCentralList();
        centralList3 = createCentralList();
        centralList4 = createCentralList();

        //assenze

        numeroAssenze = new JLabel("4");
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

        numeroRitardi = new JLabel("10");
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

        numeroPresenze = new JLabel("15");
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

        numeroUscite = new JLabel("35");
        numeroUscite.setBounds(112, -100, 350, 350);
        numeroUscite.setFont(new Font("Arial", Font.BOLD, 50));
        numeroUscite.setForeground(Color.WHITE);

        uscite = new JLabel("Uscite");
        uscite.setBounds(112, 75, 75, 75);
        uscite.setFont(new Font("Arial", Font.BOLD, 15));
        uscite.setForeground(Color.WHITE);
        centralList4.add(numeroUscite);
        centralList4.add(uscite);


        // Add the lists to JScrollPane and then to the central panel
        centralPanel.add(new JScrollPane(centralList1));
        centralPanel.add(new JScrollPane(centralList2));
        centralPanel.add(new JScrollPane(centralList3));
        centralPanel.add(new JScrollPane(centralList4));

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
}