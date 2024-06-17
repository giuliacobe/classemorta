/*
 * Iemmolo Gioele Carmelo 4Di
 * ESEGUIRE QUESTA CLASSE MAIN PER UTILIZZARE IL TOTEM (JTimbratrice GRAFICA)
 */
package it.edu.marconiverona.classemorta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author 19929
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        String url = "jdbc:h2:~/testdb";
        String user = "sa";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); BufferedReader br = new BufferedReader(new FileReader("path/to/your/schema.sql"))) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
            }

            stmt.execute(sql.toString());
            System.out.println("Schema executed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
    }
}
