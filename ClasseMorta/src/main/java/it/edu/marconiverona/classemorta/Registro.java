/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.edu.marconiverona.classemorta;

/**
 *
 * @author ferna
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Registro extends JFrame {

    private JTextField studentNameField;
    private JTextArea registroArea;
    private JComboBox<String> studentComboBox;
    private JButton addStudentButton;
    private JButton markPresenceButton;
    private JButton addGradeButton;
    private JTextField gradeField;

    private List<Studente> students;

    public Registro() {
        students = new ArrayList<>();

        setTitle("Registro Elettronico");
        setLayout(new BorderLayout());

        // Pannello superiore per aggiungere studenti
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        studentNameField = new JTextField(20);
        addStudentButton = new JButton("Aggiungi Studente");
        topPanel.add(new JLabel("Nome Studente:"));
        topPanel.add(studentNameField);
        topPanel.add(addStudentButton);

        add(topPanel, BorderLayout.NORTH);

        // Pannello centrale per mostrare il registro
        registroArea = new JTextArea();
        registroArea.setEditable(false);
        add(new JScrollPane(registroArea), BorderLayout.CENTER);

        // Pannello inferiore per registrare presenze e voti
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        studentComboBox = new JComboBox<>();
        markPresenceButton = new JButton("Segna Presenza");
        gradeField = new JTextField(5);
        addGradeButton = new JButton("Aggiungi Voto");

        bottomPanel.add(new JLabel("Seleziona Studente:"));
        bottomPanel.add(studentComboBox);
        bottomPanel.add(markPresenceButton);
        bottomPanel.add(new JLabel("Voto:"));
        bottomPanel.add(gradeField);
        bottomPanel.add(addGradeButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Azioni dei pulsanti
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = studentNameField.getText();
                if (!studentName.isEmpty()) {
                    Studente student = new Studente(studentName);
                    students.add(student);
                    studentComboBox.addItem(studentName);
                    registroArea.append("Aggiunto Studente: " + studentName + "\n");
                    studentNameField.setText("");
                }
            }
        });

        markPresenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudentName = (String) studentComboBox.getSelectedItem();
                if (selectedStudentName != null) {
                    for (Studente student : students) {
                        if (student.getName().equals(selectedStudentName)) {
                            student.markPresence();
                            registroArea.append("Presenza segnata per: " + selectedStudentName + "\n");
                        }
                    }
                }
            }
        });

        addGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudentName = (String) studentComboBox.getSelectedItem();
                String gradeText = gradeField.getText();
                if (selectedStudentName != null && !gradeText.isEmpty()) {
                    try {
                        int grade = Integer.parseInt(gradeText);
                        for (Studente student : students) {
                            if (student.getName().equals(selectedStudentName)) {
                                student.addGrade(grade);
                                registroArea.append("Voto " + grade + " aggiunto per: " + selectedStudentName + "\n");
                                gradeField.setText("");
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Inserisci un numero valido per il voto.");
                    }
                }
            }
        });

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}
