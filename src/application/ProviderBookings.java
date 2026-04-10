package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * Provider Bookings Management Screen
 * Allows provider to view pending requests and mark them as Accepted or Rejected
 */
public class ProviderBookings {

    public void show(Stage stage, String username) {

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // Back button to dashboard
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> new ProviderDashboard().show(stage, username));

        Label pendingTitle = new Label("Pending Requests");

        ListView<Booking> pendingList = new ListView<>();
        ListView<String> completedList = new ListView<>();

        // Load pending bookings from database
        List<Booking> pending = BookingDAO.getProviderPendingBookings(username);
        pendingList.getItems().addAll(pending);

        Label completedTitle = new Label("Completed Tasks");

        // Load accepted bookings and show only completed ones
        List<Booking> accepted = BookingDAO.getProviderAcceptedBookings(username);
        for (Booking b : accepted) {
            if ("Completed".equals(b.status)) {
                completedList.getItems().add(b.toString());
            }
        }

        Button accept = new Button("Accept");
        Button reject = new Button("Reject");

        accept.setVisible(false);
        reject.setVisible(false);

        // Show Accept/Reject buttons when a pending request is selected
        pendingList.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                accept.setVisible(true);
                reject.setVisible(true);
            } else {
                accept.setVisible(false);
                reject.setVisible(false);
            }
        });

        // Accept button logic
        accept.setOnAction(e -> {
            Booking selected = pendingList.getSelectionModel().getSelectedItem();

            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("Do you want to accept this request?");
                confirm.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        boolean ok = BookingDAO.updateBookingStatus(selected.id, "Accepted");
                        if (ok) {
                            pendingList.getItems().remove(selected);
                            completedList.getItems().add(selected.toString() + " (Accepted)");

                            Alert done = new Alert(Alert.AlertType.INFORMATION);
                            done.setContentText("Request accepted!");
                            done.showAndWait();

                            // Refresh dashboard
                            new ProviderDashboard().show(stage, username);
                        } else {
                            Alert err = new Alert(Alert.AlertType.ERROR);
                            err.setContentText("Failed to accept request. Try again.");
                            err.showAndWait();
                        }
                    }
                });
            }
        });

        // Reject button logic
        reject.setOnAction(e -> {
            Booking selected = pendingList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = BookingDAO.updateBookingStatus(selected.id, "Rejected");
                if (ok) {
                    pendingList.getItems().remove(selected);
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setContentText("Request rejected.");
                    info.showAndWait();
                } else {
                    Alert err = new Alert(Alert.AlertType.ERROR);
                    err.setContentText("Failed to reject request. Try again.");
                    err.showAndWait();
                }
            }
        });

        content.getChildren().addAll(
                backBtn,
                pendingTitle, pendingList,
                accept, reject,
                completedTitle, completedList
        );

        BorderPane root = new BorderPane();
        root.setCenter(content);

        stage.setScene(new Scene(root, 450, 500));
        stage.show();
    }
}