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
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class JTimbratrice extends JFrame implements ActionListener {

    private JLabel titolo;
    private JTextField fieldTesto;
    private JButton buttonEntrata, buttonUscita;
    private DefaultListModel<String> contenutoLista;
    private JList<String> lista;
    private Timbratrice timb;
    private JLabel orologioLabel; // JLabel per visualizzare l'orologio
    private Timer timer; // Timer per aggiornare l'orologio
    private Set<String> presentiOggi; // Set per tracciare i presenti


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
            try {
                handleEntrata();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == buttonUscita) {
            azione = "Uscita";
            try {
                handleUscita();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        String testo = fieldTesto.getText();
        if (!testo.isEmpty()) {
            presentiOggi.add(testo);
        }
    }

    private void handleEntrata() throws SQLException {
        String testo = fieldTesto.getText();
        if (testo.isEmpty()) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        String azione = "";
        if (now.getHour() < 8) {
            azione = "Presenza";
            String query = "UPDATE DatiLogin SET presenze = presenze + 1 WHERE fullName = ?";
            PreparedStatement stmt = Main.conn.prepareStatement(query);
            stmt.setString(1, fieldTesto.getText());
            stmt.executeQuery();
        } else if (now.getHour() > 8 && now.getHour() < 13) {
            azione = "Ritardo";
            String query = "UPDATE DatiLogin SET ritardi = ritardi + 1 WHERE fullName = ?";
            PreparedStatement stmt = Main.conn.prepareStatement(query);
            stmt.setString(1, fieldTesto.getText());
            stmt.executeQuery();
            String query7 = "UPDATE DatiLogin SET ritardiDG = ritardiDG + 1 WHERE fullName = ?";
            stmt = Main.conn.prepareStatement(query7);
            stmt.setString(1, fieldTesto.getText());
            stmt.executeQuery();


        } else if (now.getHour() > 13) {
            azione = "Assenza";
            String query = "UPDATE DatiLogin SET assenze = assenze + 1 WHERE fullName = ?";
            PreparedStatement stmt = Main.conn.prepareStatement(query);
            stmt.setString(1, fieldTesto.getText());
            stmt.executeQuery();
            String query6 = "UPDATE DatiLogin SET assenzeDG = assenzeDG + 1 WHERE fullName = ?";
            stmt = Main.conn.prepareStatement(query6);
            stmt.setString(1, fieldTesto.getText());
            stmt.executeQuery();
        }

        String elemento = azione + ": " + testo;
        contenutoLista.addElement(elemento);
        timb.timbra(testo, azione);
        fieldTesto.setText("");
    }

    private void handleUscita() throws SQLException {
        String testo = fieldTesto.getText();
        if (testo.isEmpty()) {
            return;
        }
        String query = "UPDATE DatiLogin SET uscite = uscite + 1 WHERE fullName = ?";
        PreparedStatement stmt = Main.conn.prepareStatement(query);
        stmt.setString(1, fieldTesto.getText());
        stmt.executeQuery();
        String query5 = "UPDATE DatiLogin SET usciteDG = usciteDG + 1 WHERE fullName = ?";
        stmt = Main.conn.prepareStatement(query5);
        stmt.setString(1, fieldTesto.getText());
        stmt.executeQuery();

        DataOra oraCorrente = new DataOra();
        String elemento = "Uscita: " + testo + " è l'inserimento";

        contenutoLista.addElement(elemento);
        timb.timbra(testo, "Uscita");
        fieldTesto.setText("");
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
