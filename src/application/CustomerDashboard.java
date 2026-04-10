package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

/**
 * Customer Dashboard - Main home screen for customers
 * Allows browsing services and navigating to different sections
 */
public class CustomerDashboard {

    private VBox bookingsVBox = new VBox(10); // Container to display bookings

    public void show(Stage stage, String username) {

        // ---------------- TOP CONTENT ----------------
        VBox topContent = new VBox(10);
        topContent.setPadding(new Insets(20));

        Label welcome = new Label("Welcome, " + username);
        Label location = new Label("Current Location: Buddhanagar");

        Label searchLabel = new Label("Search for services:");
        TextField searchField = new TextField();

        Label categoryLabel = new Label("Categories:");

        // Service category buttons
        Button plumbing = new Button("Plumbing");
        Button cleaning = new Button("Cleaning");
        Button painting = new Button("Painting");
        Button electrical = new Button("Electrician");

        // Open service providers screen when category is clicked
        plumbing.setOnAction(e -> new ServiceProvidersScreen().show(stage, "Plumbing", username));
        cleaning.setOnAction(e -> new ServiceProvidersScreen().show(stage, "Cleaning", username));
        painting.setOnAction(e -> new ServiceProvidersScreen().show(stage, "Painting", username));
        electrical.setOnAction(e -> new ServiceProvidersScreen().show(stage, "Electrical", username));

        topContent.getChildren().addAll(
                welcome, location,
                searchLabel, searchField,
                categoryLabel,
                plumbing, cleaning, painting, electrical
        );

        // ---------------- BOTTOM NAVIGATION ----------------
        HBox nav = new HBox(10);
        nav.setPadding(new Insets(10));

        Button homeBtn = new Button("Home");
        Button bookingBtn = new Button("Bookings");
        Button messageBtn = new Button("Messages");
        Button profileBtn = new Button("Profile");

        homeBtn.setOnAction(e -> show(stage, username));
        bookingBtn.setOnAction(e -> showBookings(stage, username));
        messageBtn.setOnAction(e -> new CustomerMessages().show(stage, username));
        profileBtn.setOnAction(e -> new CustomerProfile().show(stage, username));

        nav.getChildren().addAll(homeBtn, bookingBtn, messageBtn, profileBtn);

        // ---------------- MAIN LAYOUT ----------------
        BorderPane root = new BorderPane();
        root.setCenter(topContent);
        root.setBottom(nav);

        stage.setScene(new Scene(root, 350, 500));
        stage.show();
    }

    /**
     * Displays customer's booking history
     */
    private void showBookings(Stage stage, String username) {
        bookingsVBox.getChildren().clear();
        List<Booking> bookings = BookingDAO.getCustomerBookings(username);

        if (bookings.isEmpty()) {
            bookingsVBox.getChildren().add(new Label("No bookings yet."));
        } else {
            for (Booking b : bookings) {
                TextArea bookingArea = new TextArea(
                        "Provider: " + b.providerUsername +
                        "\nCategory: " + b.category +
                        "\nDate: " + b.date +
                        "\nTime: " + b.time +
                        "\nProblem: " + b.description +
                        "\nLocation: " + b.location +
                        "\nStatus: " + b.status
                );
                bookingArea.setEditable(false);
                bookingArea.setWrapText(true);
                bookingsVBox.getChildren().add(bookingArea);
            }
        }

        // Back button
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> show(stage, username));
        VBox layout = new VBox(10, bookingsVBox, backBtn);
        layout.setPadding(new Insets(20));

        // Bottom navigation (repeated for consistency)
        HBox nav = new HBox(10);
        nav.setPadding(new Insets(10));
        Button homeBtn = new Button("Home");
        Button bookingBtn = new Button("Bookings");
        Button messageBtn = new Button("Messages");
        Button profileBtn = new Button("Profile");

        homeBtn.setOnAction(e -> show(stage, username));
        bookingBtn.setOnAction(e -> showBookings(stage, username));
        messageBtn.setOnAction(e -> new CustomerMessages().show(stage, username));
        profileBtn.setOnAction(e -> new CustomerProfile().show(stage, username));

        nav.getChildren().addAll(homeBtn, bookingBtn, messageBtn, profileBtn);

        BorderPane root = new BorderPane();
        root.setCenter(layout);
        root.setBottom(nav);

        stage.setScene(new Scene(root, 350, 500));
    }
}