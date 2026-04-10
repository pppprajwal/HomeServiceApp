package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class
 * Manages connection to MySQL database for the entire application
 */
public class Database {
    
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/home_service_app"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "Aathrai987456321@"; 

    /**
     * Returns a Connection object to interact with the database
     * Used by all DAO classes (UserDAO, BookingDAO)
     */
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected!");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}