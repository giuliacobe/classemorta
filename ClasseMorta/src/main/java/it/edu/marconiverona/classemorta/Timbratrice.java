/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.edu.marconiverona.classemorta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author 19859
 */
public class Timbratrice{
    
    private ArrayList<Timbratura> timbrature;
    
    //Costruttore
    public Timbratrice(){
       timbrature = new ArrayList<>();
    }
    
    public void timbra(String matr , String verso){
        timbrature.add(new Timbratura(matr,verso));
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
    
    public void save(){//save
      try {
        File file = new File("timb.dat");
        // if file doesnt exists, then create it
        if (file.createNewFile())
            System.out.println("timb.dat creata");
        else
            System.out.println("timb.dat già esistente");

        FileOutputStream out = new FileOutputStream(file);
        ObjectOutputStream sout = new ObjectOutputStream(out);
        
        for (int i=0; i<timbrature.size(); i++)
        {
            sout.writeObject(timbrature.get(i));
        }
        sout.close();
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    
    
    public void load() throws FileNotFoundException{
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    int result = fileChooser.showOpenDialog(new JPanel());
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            FileInputStream in = new FileInputStream(selectedFile);
            ObjectInputStream ois = new ObjectInputStream(in);
            while(true){

                Timbratura t = (Timbratura)ois.readObject();
                timbrature.add(t);
                System.out.println(t.toString());
            }
            
            
        } catch (Exception e){
            System.out.println(timbrature.size());
        }
    }
    }
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
        
        Durata d1;
        Timbratrice totem = new Timbratrice();
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        boolean esci = true;
        int scelta= 0;
        int i = 0 ;
       while(scelta != 4){
        
        System.out.println("1 - Ingresso\n2 - Uscita\n3 - visualizza\n4 - esci");
        scelta=sc1.nextInt();
        switch(scelta){
                case 1 :
                    String v = "Entrata";
                    System.out.println("Inserire matricola : ");
                    String m = sc.nextLine();
                    totem.timbra(m,v);
                    System.out.println(totem.timbrature.get(i).toString());
                    break;
                    
                case 2 :
                    v = "Uscita";
                    System.out.println("Inserire matricola : ");
                    m = sc.nextLine();
                    totem.timbra(m,v);
                    System.out.println(totem.timbrature.get(i).toString());
                    for(int j=totem.timbrature.size()-1;j>=0;j--)
                    {
                        if(totem.timbrature.get(j).getUtente().equals(m)&& totem.timbrature.get(j).getVerso().equals("Entrata"))
                        {
                            d1=new Durata(totem.timbrature.get(j),totem.timbrature.get(i));
                            System.out.println(d1.toString());
                            break;
                        }
                    }
                    
                    break;
                case 3 :
                    for(int j = 0 ; j<totem.timbrature.size() ; j++ ){
                    System.out.println(totem.timbrature.get(j) + "\n");
                    }
                    break;

                case 4 :
                    System.out.println("Sei uscito");
                    
                    break;
                    
            }
        i++;
            
        }
    }
}
                
         
