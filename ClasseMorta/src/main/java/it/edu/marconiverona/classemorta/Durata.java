package it.edu.marconiverona.classemorta;



public class Durata {

    long secondi;

    public Durata(DataOra ini, DataOra end) {
        secondi = end.get() - ini.get();
    }
    public long get()
    {
        return secondi;
    }
    public int getMins()
    {
        return (int)((secondi-getHours () *3600)/60);
    }
    public long getHours()
    {
        return secondi/3600;
    }
    
    
    public void add(Durata i ) {
        secondi += i.secondi;
    }
    
    public String toString() {
        return "Tempo Trascorso Ore: "+getHours()+" Minuti: "+getMins();
    }
}