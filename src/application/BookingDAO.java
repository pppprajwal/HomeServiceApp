package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingDAO (Data Access Object)
 * Handles all database operations related to bookings/services
 */
public class BookingDAO {

    /**
     * Adds a new booking and returns the generated booking ID
     * Returns >0 on success, -1 on failure
     */
    public static int addBookingReturnId(String customer, String provider, String category, int price,
                                     String date, String time, String description, String location) {
        String sql = "INSERT INTO bookings(customer_username, provider_username, category, service_price, date, time, problem_description, location, status) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, customer);
            stmt.setString(2, provider);
            stmt.setString(3, category);
            stmt.setInt(4, price);
            stmt.setString(5, date);
            stmt.setString(6, time);
            stmt.setString(7, description);
            stmt.setString(8, location);
            stmt.setString(9, "Pending");
            
            int affected = stmt.executeUpdate();
            if (affected == 0) return -1;
            
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);   // return auto-generated booking ID
                }
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Backwards compatible method - returns true/false instead of ID
     */
    public static boolean addBooking(String customer, String provider, String category, int price,
                                     String date, String time, String description, String location) {
        return addBookingReturnId(customer, provider, category, price, date, time, description, location) > 0;
    }

    /**
     * Returns all bookings made by a specific customer (newest first)
     */
    public static List<Booking> getCustomerBookings(String customer) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customer_username=? ORDER BY id DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapBooking(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns pending booking requests for a provider
     */
    public static List<Booking> getProviderPendingBookings(String provider) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE provider_username=? AND status='Pending' ORDER BY id DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, provider);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapBooking(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns accepted bookings for a provider (sorted by date and time)
     */
    public static List<Booking> getProviderAcceptedBookings(String provider) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE provider_username=? AND status='Accepted' ORDER BY date,time";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, provider);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapBooking(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns all bookings for a provider
     */
    public static List<Booking> getProviderBookings(String provider) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE provider_username=? ORDER BY id DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, provider);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapBooking(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Updates the status of a booking (Pending → Accepted → Completed, etc.)
     */
    public static boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Helper method to convert ResultSet row into a Booking object
     */
    private static Booking mapBooking(ResultSet rs) throws SQLException {
        return new Booking(
            rs.getInt("id"),
            rs.getString("customer_username"),
            rs.getString("provider_username"),
            rs.getString("category"),
            rs.getString("date"),
            rs.getString("time"),
            rs.getString("problem_description"),
            rs.getString("location"),
            rs.getString("status"),
            rs.getInt("service_price")
        );
    }
}