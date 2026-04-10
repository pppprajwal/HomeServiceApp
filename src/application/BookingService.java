package application;

import java.util.ArrayList;

/**
 * BookingService class
 * Holds bookings in memory (in-memory storage)
 * 
 * BookingDAO with database instead of this in-memory list.
 */
public class BookingService {

    // Static list to store bookings temporarily in memory
    public static ArrayList<Booking> bookings = new ArrayList<>();

    /**
     * Adds a booking to the in-memory list
     */
    public static void addBooking(Booking b) {
        bookings.add(b);
    }

    /**
     * Returns all bookings stored in memory
     */
    public static ArrayList<Booking> getBookings() {
        return bookings;
    }
}