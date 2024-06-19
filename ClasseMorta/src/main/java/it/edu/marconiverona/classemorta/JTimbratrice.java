package it.edu.marconiverona.classemorta;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JTimbratrice extends JFrame implements ActionListener {

    private JLabel titolo;
    private JTextField fieldTesto;
    private JButton buttonEntrata, buttonUscita;
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

        // Caricamento e ridimensionamento dell'immagine con alta qualità usando imgscalr
        try {
            BufferedImage originalImage = ImageIO.read(Main.class.getClassLoader().getResource("SCRITTA_PNG.png"));
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            int targetWidth = 150; // Riduzione ulteriore della larghezza
            int targetHeight = (int) ((double) targetWidth / originalWidth * originalHeight); // Calcolo dell'altezza proporzionale
            ImageIcon icon = resizeIconWithScalr(originalImage, targetWidth, targetHeight);
            titolo = new JLabel(icon);
        } catch (IOException e) {
            e.printStackTrace();
            titolo = new JLabel("TOTEM"); // Testo di fallback in caso di errore
        }
        titolo.setBounds(225, 20, 150, 60); // Leggera riduzione delle dimensioni

        fieldTesto = new JTextField("");
        fieldTesto.setBounds(200, 120, 200, 30);
        fieldTesto.setHorizontalAlignment(SwingConstants.CENTER);
        fieldTesto.setBackground(new Color(255, 255, 240));

        buttonEntrata = new JButton("ENTRATA");
        buttonEntrata.setBounds(150, 350, 100, 40);
        buttonEntrata.addActionListener(this);

        buttonUscita = new JButton("USCITA");
        buttonUscita.setBounds(350, 350, 100, 40);
        buttonUscita.addActionListener(this);

        enableDefaultValue(fieldTesto, "Matricola");

        contenutoLista = new DefaultListModel<>();
        lista = new JList<>(contenutoLista);
        JScrollPane listaScrollabile = new JScrollPane(lista);
        listaScrollabile.setBounds(150, 170, 300, 150);

        timb = new Timbratrice();

        // Modifica Label per Orologio
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

        add(titolo); // Aggiunge titolo al frame
        add(fieldTesto);
        add(buttonEntrata);
        add(buttonUscita);
        add(listaScrollabile);
        add(orologioLabel); // Aggiunge orologio al frame

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String azione = "";
        if (e.getSource() == buttonEntrata) {
            azione = "Entrata";
        } else if (e.getSource() == buttonUscita) {
            azione = "Uscita";
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
        if (!fieldTesto.getText().isEmpty()) {
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

    // Metodo per abilitare il valore predefinito nel JTextField
    public static void enableDefaultValue(final JTextField tf, final String defaultValue) {
        tf.setText(defaultValue);
        tf.setForeground(Color.gray);
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tf.getText().equals(defaultValue)) {
                    tf.setForeground(Color.black);
                    tf.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tf.getText().isEmpty()) {
                    tf.setForeground(Color.gray);
                    tf.setText(defaultValue);
                }
            }
        });
    }

    // Metodo per ridimensionare l'icona con imgscalr
    private ImageIcon resizeIconWithScalr(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
        return new ImageIcon(resizedImage);
    }
}
