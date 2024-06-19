/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.edu.marconiverona.classemorta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * @author 19859
 */
public class Timbratrice {

    private ArrayList<Timbratura> timbrature;

    //Costruttore
    public Timbratrice() {
        timbrature = new ArrayList<>();
    }

    public void timbra(String matr, String verso) {
        timbrature.add(new Timbratura(matr, verso));
    }

    //Cerca l'ultima entrata di una matricola
    public Timbratura getUltimaEntrata(String matr) {
        for (int i = timbrature.size() - 1; i >= 0; i--) {
            Timbratura timbratura = timbrature.get(i);
            if (timbratura.getVerso().equals("Entrata") && timbratura.getUtente().equals(matr)) {
                return timbratura;
            }
        }
        return null;
    }
}
                
         

