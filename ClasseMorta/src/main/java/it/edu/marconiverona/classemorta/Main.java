/*
 * Iemmolo Gioele Carmelo 4Di
 * ESEGUIRE QUESTA CLASSE MAIN PER UTILIZZARE IL TOTEM (JTimbratrice GRAFICA)
 */
package it.edu.marconiverona.classemorta;

import java.io.*;
import java.sql.*;

/**
 * @author 19929
 */
public class Main {
    public static String url = "jdbc:oracle:thin:@//oraclecorso.addvalue.it:1521/XEPDB1";
    public static String user = "USERCORSO3";
    public static String password = "usercorso3";
    public static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Statement stmt;

    static {
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        InterfacciaPrima.creazione();
    }
}
