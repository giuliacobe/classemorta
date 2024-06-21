package it.edu.marconiverona.classemorta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Login extends javax.swing.JFrame {

    public static javax.swing.JTextField jTextField1 = new  javax.swing.JTextField();


    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        setPreferredSize(new java.awt.Dimension(800, 500));
        setResizable(false);
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        Right.setBackground(new java.awt.Color(204, 4, 4));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel5.setIcon(new javax.swing.ImageIcon(Main.class.getClassLoader().getResource("logo2.png")));

        jLabel6.setFont(new java.awt.Font("Showcard Gothic", 1, 24));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14));
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("copyright © ClasseMorta All rights reserved");

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
                RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightLayout.createSequentialGroup()
                                .addGap(0, 81, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(40, 40, 40))
                        .addGroup(RightLayout.createSequentialGroup()
                                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(RightLayout.createSequentialGroup()
                                                .addGap(103, 103, 103)
                                                .addComponent(jLabel6))
                                        .addGroup(RightLayout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(jLabel5)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RightLayout.setVerticalGroup(
                RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RightLayout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addComponent(jLabel5)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(78, 78, 78))
        );

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36));
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("LOGIN");

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel2.setText("Email");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel3.setText("Password");

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String query = "SELECT email, password FROM DatiLogin WHERE email = ?";
                try (PreparedStatement stmt = Main.conn.prepareStatement(query)) {
                    stmt.setString(1, jTextField1.getText());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            String emailFromDB = rs.getString("email");
                            String passFromDB = rs.getString("password");
                            if (passFromDB.equals(getPassword()) && emailFromDB.equals(jTextField1.getText())) {
                                afterLogin();
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        jLabel4.setText("I don't have an account");

        this.enableDefaultValue(jTextField1, "example@example.com");
        this.enableDefaultValue(jPasswordField1, "********");

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton2.setForeground(new java.awt.Color(255, 51, 51));
        jButton2.setText("Sign Up");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
                LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .

                        addGroup(LeftLayout.createSequentialGroup()
                                        .

                                addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .

                                        addGroup(LeftLayout.createSequentialGroup()
                                                        .

                                                addGap(138, 138, 138)
                                                        .

                                                addComponent(jLabel1))
                                                .

                                        addGroup(LeftLayout.createSequentialGroup()
                                                        .

                                                addGap(30, 30, 30)
                                                        .

                                                addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .

                                                        addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).
                                                                addComponent(jLabel2).
                                                                addComponent(jTextField1).
                                                                addComponent(jLabel3).
                                                                addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE).
                                                                addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)).
                                                        addGroup(LeftLayout.createSequentialGroup().
                                                                addComponent(jLabel4).
                                                                addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).
                                                                addComponent(jButton2))))).
                                addContainerGap(27, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
                LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).
                        addGroup(LeftLayout.createSequentialGroup().
                                addGap(51, 51, 51).
                                addComponent(jLabel1).
                                addGap(40, 40, 40).
                                addComponent(jLabel2).
                                addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).
                                addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE).
                                addGap(18, 18, 18).
                                addComponent(jLabel3).
                                addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).
                                addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE).
                                addGap(26, 26, 26).
                                addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE).
                                addGap(33, 33, 33).

                                addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .

                                        addComponent(jLabel4)
                                                .

                                        addComponent(jButton2))
                                        .

                                addContainerGap(77, Short.MAX_VALUE))
        );

        jButton3 = new javax.swing.JButton();
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton3.setText("SPID");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avviaImmagine();
            }
        });
        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setBounds(250, 327, 93, 36);
        Left.add(jButton3);
        jPanel1.add(Left);
        Left.setBounds(400, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

        getContentPane().

                setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .

                        addGroup(layout.createSequentialGroup().

                                addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).
                                addGap(0, 129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).
                        addGroup(layout.createSequentialGroup().
                                addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).
                                addGap(0, 149, Short.MAX_VALUE))
        );
        getAccessibleContext().
                setAccessibleName("LOGIN");
        pack();

        jButton4 = new javax.swing.JButton();
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton4.setText("Home");
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setOpaque(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setBorderPainted(false);
        jButton4.setDefaultCapable(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tornaIndietro();
            }
        });
        jButton4.setBackground(null);
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton4.setBounds(-20, -5, 93, 36);
        Left.add(jButton4);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4.setForeground(new java.awt.Color(255, 0, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4.setForeground(new java.awt.Color(0, 0, 0));
            }
        });


    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

        SignUp SignUpFrame = new SignUp();
        SignUpFrame.setVisible(true);
        SignUpFrame.pack();
        SignUpFrame.setLocationRelativeTo(null);
        this.dispose();
    }

    public void afterLogin() throws SQLException {
        RegistroElettronicoApp app = new RegistroElettronicoApp();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        this.dispose();
    }

    public String getPassword() {
        return new String(jPasswordField1.getPassword());
    }

    public void avviaImmagine() {
        EventQueue.invokeLater(new Menu());
        this.dispose();
    }

    public void enableDefaultValue(final JTextField tf, final String defaultValue) {
        tf.setText(defaultValue);
        tf.setForeground(Color.gray);
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tf.getText().equals(defaultValue)) {
                    tf.setForeground(Color.black);
                    tf.setText("");
                }
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tf.getText().isEmpty()) {
                    tf.setForeground(Color.gray);
                    tf.setText(defaultValue);
                }
                super.focusLost(e);
            }
        });
    }

    public static String getFullName() {
        String fullName = null;
        String query = "SELECT fullName FROM DatiLogin WHERE email = ?";
        try (PreparedStatement stmt = Main.conn.prepareStatement(query)) {
            stmt.setString(1, jTextField1.getText());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    fullName = rs.getString("fullName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullName;
    }

    public void tornaIndietro(){
        InterfacciaPrima.creazione();
        this.dispose();
    }

    private javax.swing.JPanel Left;
    private javax.swing.JPanel Right;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
}
