package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/kost";
    private static final String USER = "root";
    private static final String PASSWORD = "BakutBebur";

    private static Connection connection = null;

    public static Connection getConnection() {
        System.out.println(connection);
        try {
            if(connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi ke Database berhasil");
                System.out.println(connection);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL tidak ditemukan: " + e.getMessage());
            System.out.println(connection);
        } catch (SQLException e) {
            System.out.println("Gagal koneksi ke Database: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Koneksi ke Database ditutup");
            }
        } catch(SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
