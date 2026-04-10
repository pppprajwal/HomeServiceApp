package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Provider Messages Screen
 * Placeholder for messaging feature (currently not implemented)
 */
public class ProviderMessages {

    public void show(Stage stage, String username) {

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label title = new Label("Messages");
        Label empty = new Label("No messages yet");

        Button backBtn = new Button("Back");

        // Back to Provider Dashboard
        backBtn.setOnAction(e -> new ProviderDashboard().show(stage, username));

        content.getChildren().addAll(title, empty, backBtn);

        // Bottom Navigation
        HBox nav = new HBox(10);
        nav.setPadding(new Insets(10));

        Button home = new Button("Home");
        Button bookings = new Button("Bookings");
        Button messages = new Button("Messages");
        Button profile = new Button("Profile");

        home.setOnAction(e -> new ProviderDashboard().show(stage, username));
        bookings.setOnAction(e -> new ProviderBookings().show(stage, username));
        messages.setOnAction(e -> show(stage, username));
        profile.setOnAction(e -> new ProviderProfile().show(stage, username));

        nav.getChildren().addAll(home, bookings, messages, profile);

        BorderPane root = new BorderPane();
        root.setCenter(content);
        root.setBottom(nav);

        stage.setScene(new Scene(root, 300, 400));
    }
}