package it.edu.marconiverona.classemorta;


public class Timbratura extends DataOra implements java.io.Serializable {
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
 


