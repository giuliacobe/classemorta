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
    public static String url = "jdbc:mysql://localhost:3306/classemortadb?useSSL=false";
    public static String user = "root";
    public static String password = "";
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
        Class.forName("com.mysql.cj.jdbc.Driver");
        InterfacciaPrima.creazione();
    }
}
