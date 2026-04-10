package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Customer Bookings Screen (Simple ListView version)
 * Displays all bookings made by the logged-in customer
 */
public class CustomerBookings {

    public void show(Stage stage, String username) {

        Label title = new Label("My Bookings");

        ListView<String> list = new ListView<>();

        // Fetch bookings from database
        List<Booking> bookings = BookingDAO.getCustomerBookings(username);
        for (Booking b : bookings) {
            list.getItems().add(b.toString());
        }

        if (list.getItems().isEmpty()) {
            list.getItems().add("No bookings yet");
        }

        Button back = new Button("Back");
        back.setOnAction(e -> new CustomerDashboard().show(stage, username));

        VBox layout = new VBox(10, title, list, back);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 350, 400));
    }
}