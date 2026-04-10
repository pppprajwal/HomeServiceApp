package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * UserDAO (Data Access Object)
 * Handles all database operations related to users (Customer and Provider)
 */
public class UserDAO {

    /**
     * Registers a new user (Customer or Provider) into the database
     */
    public static boolean registerUser(String username, String password, String role, String location) {
        String sql = "INSERT INTO users(username, password, role, location) VALUES(?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, location);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates username and password during login
     */
    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // returns true if user exists

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the role (Customer or Provider) of a user
     */
    public static String getRole(String username) {
        String sql = "SELECT role FROM users WHERE username=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the location/address of a provider from the users table
     */
    public static String getProviderLocation(String username) {
        String sql = "SELECT location FROM users WHERE username=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("location");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}