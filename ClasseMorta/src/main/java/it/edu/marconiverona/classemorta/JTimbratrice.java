/*
 * Iemmolo Gioele Carmelo 4Di
 */
package it.edu.marconiverona.classemorta;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Utilizziamo DateTimeFormatter per formattare l'orario

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JTimbratrice extends JFrame implements ActionListener {

    private JLabel titolo;
    private JTextField fieldTesto;
    private JButton buttonEntrata, buttonUscita, buttonSave, buttonLoad;
    private DefaultListModel<String> contenutoLista;
    private JList<String> lista;
    private Timbratrice timb;
    private JLabel orologioLabel; // JLabel per visualizzare l'orologio
    private Timer timer; // Timer per aggiornare l'orologio

    public JTimbratrice() {
        setTitle("Timbratrice");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 242, 235));

        titolo = new JLabel("TOTEM");
        titolo.setBounds(250, 10, 100, 40);
        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        titolo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        titolo.setBackground(new Color(255, 255, 245));
        titolo.setOpaque(true);
        titolo.setFont(new Font("Times New Roman", Font.BOLD, 16));
        titolo.setForeground(Color.DARK_GRAY);

        fieldTesto = new JTextField("");
        fieldTesto.setBounds(200, 80, 200, 30);
        fieldTesto.setHorizontalAlignment(SwingConstants.CENTER);
        fieldTesto.setBackground(new Color(255, 255, 240));
        fieldTesto.setToolTipText("Inserire il testo");

        buttonEntrata = new JButton("ENTRATA");
        buttonEntrata.setBounds(150, 130, 100, 40);
        buttonEntrata.addActionListener(this);

        buttonSave = new JButton("SAVE");
        buttonSave.setBounds(150, 370, 100, 40);
        buttonSave.addActionListener(this);

        buttonUscita = new JButton("USCITA");
        buttonUscita.setBounds(350, 130, 100, 40);
        buttonUscita.addActionListener(this);

        buttonLoad = new JButton("LOAD");
        buttonLoad.setBounds(350, 370, 100, 40);
        buttonLoad.addActionListener(this);

        contenutoLista = new DefaultListModel<>();
        lista = new JList<>(contenutoLista);
        JScrollPane listaScrollabile = new JScrollPane(lista);
        listaScrollabile.setBounds(150, 200, 300, 150);

        timb = new Timbratrice();
        //Modifiche Label per Orologio
        orologioLabel = new JLabel();
        orologioLabel.setBounds(200, 420, 200, 40);
        orologioLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orologioLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Timer per aggiornare l'orologio ogni secondo
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClock(); // Chiama il metodo per aggiornare l'orologio
            }
        });
        timer.start(); // Avvia il timer

        add(titolo);
        add(fieldTesto);
        add(buttonEntrata);
        add(buttonLoad);
        add(buttonSave);
        add(buttonUscita);
        add(listaScrollabile);
        add(orologioLabel); // Aggiunta orologio al frame

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String azione = "";
        if (e.getSource() == buttonEntrata) {
            azione = "Entrata";
        } else if (e.getSource() == buttonUscita) {
            azione = "Uscita";
        } else if (e.getSource() == buttonLoad) {
            try {
                timb.load();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JTimbratrice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == buttonSave) {
            timb.save();
        }

        DataOra oraCorrente = new DataOra();

        String testo = fieldTesto.getText();
        String elemento = azione + ": " + testo + " è l'inserimento";

        if (azione.equals("Uscita")) {
            Timbratura entrata = timb.getUltimaEntrata(fieldTesto.getText());
            if (entrata != null) {
                Durata durata = new Durata(entrata, oraCorrente);
                elemento += " - Durata: " + durata.toString();
            } else {
                elemento += " - Nessuna timbratura di entrata precedente";
            }
        }
        if (fieldTesto.getText().isEmpty() == false) {
            contenutoLista.addElement(elemento);
            timb.timbra(testo, azione);
            fieldTesto.setText("");
        }

    }

    // Metodo per aggiornare l'orologio
    public void updateClock() {
        LocalDateTime now = LocalDateTime.now(); // Ottiene l'orario corrente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Formatta l'orario
        String formattedDateTime = now.format(formatter); // Applica il formato all'orario
        orologioLabel.setText(formattedDateTime); // Aggiorna l'etichetta dell'orologio
    }
}
