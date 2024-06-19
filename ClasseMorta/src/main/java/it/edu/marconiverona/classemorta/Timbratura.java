/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.edu.marconiverona.classemorta;



/**
 *Iemmolo Gioele
 * 
 * Classe: 4DI - 21/12/2023
 *  
 */
public class Timbratura extends DataOra implements java.io.Serializable{
    private String codiceUtente;
    private String verso; // "Entrata" o "Uscita"
    

    public Timbratura(String codiceUtente, String verso)
    {
        super();
        this.codiceUtente = codiceUtente;
        this.verso = verso;
        
    }
    
    public String getUtente()
    {
        return codiceUtente;
    }
    public String getVerso()
    {
        return verso;
    }
    @Override
    public String toString() 
    {
        return super.toString() + " - " + codiceUtente + " " + verso;
    }
}
 


