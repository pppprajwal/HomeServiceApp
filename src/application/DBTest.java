package application;

import java.sql.Connection;

/**
 * Simple test class to verify database connection
 * Run this class separately to check if MySQL is properly connected
 */
public class DBTest {
    public static void main(String[] args) {
        Connection conn = Database.getConnection(); // calls the connection
        
        if (conn != null) {
            System.out.println("✅ Connection successful!");
        } else {
            System.out.println("❌ Connection failed!");
        }
    }
}