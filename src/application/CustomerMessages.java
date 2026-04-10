package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Customer Messages Screen
 * Placeholder screen for messaging feature (currently empty)
 */
public class CustomerMessages {

    public void show(Stage stage, String username) {

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label title = new Label("Messages");
        Label empty = new Label("No messages yet");

        Button back = new Button("Back");
        back.setOnAction(e -> new CustomerDashboard().show(stage, username));

        content.getChildren().addAll(title, empty, back);

        // Bottom Navigation
        HBox nav = new HBox(10,
                new Button("Home"),
                new Button("Bookings"),
                new Button("Messages"),
                new Button("Profile")
        );

        BorderPane root = new BorderPane();
        root.setCenter(content);
        root.setBottom(nav);

        stage.setScene(new Scene(root, 300, 400));
    }
}