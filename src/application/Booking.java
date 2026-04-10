package application;

/**
 * Booking Model Class
 * Represents a single booking/service request in the system
 */
public class Booking {
    public int id;
    public String customerUsername;
    public String providerUsername;
    public String category;
    public String date;
    public String time;
    public String description;
    public String location;
    public String status;        // Pending / Accepted / Rejected / Completed
    public int servicePrice;

    /**
     * Constructor to create a Booking object
     */
    public Booking(int id, String customerUsername, String providerUsername, String category,
                   String date, String time, String description, String location,
                   String status, int servicePrice) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.providerUsername = providerUsername;
        this.category = category;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
        this.status = status;
        this.servicePrice = servicePrice;
    }

    /**
     * Returns a human-readable string representation of the booking
     * Used in ListView and debugging
     */
    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %s",
                customerUsername,
                providerUsername != null ? providerUsername : "-",
                category != null ? category : "-",
                date != null ? date : "-",
                time != null ? time : "-",
                status != null ? status : "-"
        );
    }

    // Getter methods used by UI components
    public int getId() { return id; }
    public String getCustomerName() { return customerUsername; }
    public String getProviderUsername() { return providerUsername; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }
    public int getServicePrice() { return servicePrice; }
}