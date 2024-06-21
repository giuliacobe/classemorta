package it.edu.marconiverona.classemorta;

import java.util.Calendar;



public class DataOra {
    private Calendar c;
    private long istante; // secondi trascorsi da inizio Epoca (01/01/1970)

    public DataOra() {
        c = Calendar.getInstance();
        istante = c.getTimeInMillis() / 1000;
    }

    public DataOra(int gg, int mm, int aa, int hh, int mn, int ss) {
        c = Calendar.getInstance();
        c.set(aa, mm- 1, gg, hh, mn, ss);
        istante = c.getTimeInMillis() / 1000;
    }

    // imposta un istante specifico
    public void set(int gg, int mm, int aa, int hh, int mn, int ss) {
        c.set(aa, mm - 1, gg, hh, mn, ss);
        istante = c.getTimeInMillis() / 1000;
    }

    public long get() {
        return istante;
    }

    // avanza l'istante di offset secondi
    public void add(long offset) {
        int max = Integer.MAX_VALUE;
        while (offset > 0) {
            c.add(Calendar.SECOND, (int) offset);
            offset -= Integer.MAX_VALUE;
        }
        istante = c.getTimeInMillis() / 1000;
    }

    @Override
    public String toString() {
        int gg, mm, aa, hh, mn, ss;
        gg = c.get(Calendar.DAY_OF_MONTH);
        mm = c.get(Calendar.MONTH) + 1;
        aa = c.get(Calendar.YEAR);
        hh = c.get(Calendar.HOUR_OF_DAY);
        mn = c.get(Calendar.MINUTE);
        ss = c.get(Calendar.SECOND);
        return ("" + to2Digit(gg) + "/" + to2Digit(mm) + "/" + to2Digit(aa) + " " + to2Digit(hh)
                + ":" + to2Digit(mn) + ":" + to2Digit(ss));
    }

    private String to2Digit(int n) {
        if (n < 10) return "0" + n;
        return "" + n;
    }
}
