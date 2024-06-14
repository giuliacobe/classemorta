/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.edu.marconiverona.classemorta;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ferna
 */
class Studente {

    private String name;
    private int presences;
    private List<Integer> grades;

    public Studente(String name) {
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
