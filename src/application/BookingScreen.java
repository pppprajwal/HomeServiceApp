package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Booking Screen
 * This is where the customer fills details to book a service from a provider.
 * After submission, the booking is saved in the database with "Pending" status.
 */
public class BookingScreen {

    /**
     * Displays the booking form
   
     */
    public void show(Stage stage, String provider, String service, int price, String customerUsername) {

        Label title = new Label("Booking: " + service + " with " + provider);

        // Date selection
        Label dateLabel = new Label("Select Date:");
        DatePicker datePicker = new DatePicker();

        // Time input (manual entry)
        Label timeLabel = new Label("Select Time:");
        TextField timeField = new TextField();
        timeField.setPromptText("Enter time (e.g. 10:30 AM)");

        // Problem description
        Label problemLabel = new Label("Problem Description:");
        TextArea problem = new TextArea();

        // Location of the service
        Label locationLabel = new Label("Location:");
        TextField location = new TextField();

        // Action buttons
        Button submit = new Button("Submit Request (Estimated NPR " + price + ")");
        Button backBtn = new Button("Back");

        Label status = new Label();

        // Submit booking logic
        submit.setOnAction(e -> {
            if (datePicker.getValue() == null) {
                status.setText("Please select date!");
            } else if (timeField.getText().isEmpty()) {
                status.setText("Please enter time!");
            } else if (problem.getText().isEmpty()) {
                status.setText("Please enter problem description!");
            } else if (location.getText().isEmpty()) {
                status.setText("Please enter location!");
            } else {
                // Get values from form
                String date = datePicker.getValue().toString();
                String time = timeField.getText();
                String desc = problem.getText();
                String loc = location.getText();

                // Save booking to database using BookingDAO
                int newId = BookingDAO.addBookingReturnId(customerUsername, provider, service, price, date, time, desc, loc);
                
                if (newId > 0) {
                    // Also add to in-memory list for legacy compatibility
                    Booking b = new Booking(newId, customerUsername, provider, service, date, time, desc, loc, "Pending", price);
                    BookingService.addBooking(b);

                    status.setText("Request Sent Successfully!");
                } else {
                    status.setText("Failed to send request. Try again.");
                }
            }
        });

        // Back button returns to provider profile
        backBtn.setOnAction(e -> {
            new ProviderProfileScreen().show(stage, provider, service, customerUsername);
        });

        // Main layout
        VBox layout = new VBox(10,
                title,
                dateLabel, datePicker,
                timeLabel, timeField,
                problemLabel, problem,
                locationLabel, location,
                submit,
                backBtn,
                status
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 500));
    }
}