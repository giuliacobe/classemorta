package it.edu.marconiverona.classemorta;

import java.util.ArrayList;



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
                
         

