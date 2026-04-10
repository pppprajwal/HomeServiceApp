package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * Provider Dashboard - Main home screen for service providers
 * Shows overview: pending requests, completed jobs, earnings, and today's schedule
 */
public class ProviderDashboard {

    public void show(Stage stage, String username) {

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        // Header section
        Label welcome = new Label("Welcome, " + username);

        // Fetch provider's location from database
        String locationText = UserDAO.getProviderLocation(username);
        Label location = new Label("Address: " + (locationText != null ? locationText : "Baneshwor"));

        // Online/Offline toggle button
        ToggleButton statusBtn = new ToggleButton("Online");
        statusBtn.setSelected(true);
        statusBtn.setOnAction(e -> {
            if (statusBtn.isSelected()) {
                statusBtn.setText("Online");
            } else {
                statusBtn.setText("Offline");
            }
        });

        // Cards Section: Pending, Completed, Earnings
        HBox cards = new HBox(15);

        // Fetch all bookings for this provider
        List<Booking> providerBookings = BookingDAO.getProviderBookings(username);

        int pendingCount = 0;
        int completedCount = 0;
        int earnings = 0;

        // Calculate counts and earnings from bookings
        for (Booking b : providerBookings) {
            String status = b.getStatus() != null ? b.getStatus() : "";
            if ("Pending".equalsIgnoreCase(status)) pendingCount++;
            if ("Completed".equalsIgnoreCase(status)) {
                completedCount++;
                earnings += b.getServicePrice();
            }
        }

        // Mutable arrays to allow updating labels from inside lambdas
        final int[] pendingCountArr = new int[]{pendingCount};
        final int[] completedCountArr = new int[]{completedCount};
        final int[] earningsArr = new int[]{earnings};

        Label pendingLabel = new Label(String.valueOf(pendingCountArr[0]));
        Label completedLabel = new Label(String.valueOf(completedCountArr[0]));
        Label earningsLabel = new Label("NPR " + earningsArr[0]);

        // Pending Requests Card
        VBox pendingCard = new VBox(5);
        pendingCard.getChildren().addAll(
                new Label("Pending Requests"),
                pendingLabel
        );
        pendingCard.setStyle(
                "-fx-border-color: black; -fx-padding: 10; -fx-background-color: #f0f0f0; -fx-border-radius: 5; -fx-background-radius: 5;"
        );

        // Completed Jobs Card
        VBox completedCard = new VBox(5);
        completedCard.getChildren().addAll(
                new Label("Completed Jobs"),
                completedLabel
        );
        completedCard.setStyle(
                "-fx-border-color: black; -fx-padding: 10; -fx-background-color: #e0ffe0; -fx-border-radius: 5; -fx-background-radius: 5;"
        );

        // Earnings Card
        VBox earningsCard = new VBox(5);
        earningsCard.getChildren().addAll(
                new Label("Earnings"),
                earningsLabel
        );
        earningsCard.setStyle(
                "-fx-border-color: black; -fx-padding: 10; -fx-background-color: #e0f7ff; -fx-border-radius: 5; -fx-background-radius: 5;"
        );

        cards.getChildren().addAll(pendingCard, completedCard, earningsCard);

        // Today's Schedule - Accepted but not yet completed bookings
        Label scheduleTitle = new Label("Today's Schedule");
        ListView<Booking> schedule = new ListView<>();

        // Add only Accepted bookings to the schedule
        for (Booking b : providerBookings) {
            String status = b.getStatus() != null ? b.getStatus() : "";
            if ("Accepted".equalsIgnoreCase(status) && !"Completed".equalsIgnoreCase(status)) {
                schedule.getItems().add(b);
            }
        }

        // Custom cell factory to show booking info with a "Completed" button
        schedule.setCellFactory(lv -> new ListCell<Booking>() {
            private final Label info = new Label();
            private final Button completeBtn = new Button("Completed");
            private final HBox row = new HBox(10);

            {
                row.setPadding(new Insets(5));
                row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

                Region spacer = new Region();

                info.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(info, Priority.ALWAYS);
                HBox.setHgrow(spacer, Priority.ALWAYS);

                completeBtn.setMinWidth(90);
                completeBtn.setManaged(true);

                row.getChildren().addAll(info, spacer, completeBtn);

                completeBtn.setVisible(false);
                completeBtn.setFocusTraversable(true);
            }

            @Override
            protected void updateItem(Booking b, boolean empty) {
                super.updateItem(b, empty);
                if (empty || b == null) {
                    completeBtn.visibleProperty().unbind();
                    setGraphic(null);
                    setText(null);
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                } else {
                    info.setText(b.getTime() + " - " + b.getCategory() + " (" + b.getCustomerName() + ")");

                    completeBtn.setVisible(true);

                    // Mark booking as completed when button is clicked
                    completeBtn.setOnAction(ev -> {
                        boolean ok = BookingDAO.updateBookingStatus(b.getId(), "Completed");
                        if (ok) {
                            // Update dashboard counters
                            completedCountArr[0] = completedCountArr[0] + 1;
                            earningsArr[0] = earningsArr[0] + b.getServicePrice();
                            completedLabel.setText(String.valueOf(completedCountArr[0]));
                            earningsLabel.setText("NPR " + earningsArr[0]);

                            // Remove from schedule list
                            getListView().getItems().remove(b);

                            Alert a = new Alert(Alert.AlertType.INFORMATION, "Marked booking as completed and customer will see status updated.", ButtonType.OK);
                            a.showAndWait();
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Failed to update booking status. Try again.", ButtonType.OK);
                            a.showAndWait();
                        }
                    });

                    setText(null);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(row);
                }
            }
        });

        // Assemble all content
        content.getChildren().addAll(
                welcome,
                location,
                statusBtn,
                cards,
                scheduleTitle,
                schedule
        );

        // Bottom Navigation Bar
        HBox nav = new HBox(10);
        nav.setPadding(new Insets(10));

        Button home = new Button("Home");
        Button bookings = new Button("Bookings");
        Button messages = new Button("Messages");
        Button profile = new Button("Profile");

        home.setOnAction(e -> show(stage, username));
        bookings.setOnAction(e -> new ProviderBookings().show(stage, username));
        messages.setOnAction(e -> new ProviderMessages().show(stage, username));
        profile.setOnAction(e -> new ProviderProfile().show(stage, username));

        nav.getChildren().addAll(home, bookings, messages, profile);

        BorderPane root = new BorderPane();
        root.setCenter(content);
        root.setBottom(nav);

        stage.setScene(new Scene(root, 450, 600));
        stage.show();
    }
}