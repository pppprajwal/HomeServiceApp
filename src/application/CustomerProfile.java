package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Customer Profile Screen
 * Allows customer to view and edit their basic profile information
 */
public class CustomerProfile {
//Encapulation : All fields are private and accessed through methods (getters/setters) if needed in the future
    private String nameValue;
    private String contactValue = "98XXXXXXXX";
    private String emailValue = "user@gmail.com";

    public void show(Stage stage, String username) {

        nameValue = username;

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label title = new Label("Profile");

        // Profile fields
        TextField name = new TextField(nameValue);
        TextField contact = new TextField(contactValue);
        TextField email = new TextField(emailValue);

        // Initially disable editing
        name.setDisable(true);
        contact.setDisable(true);
        email.setDisable(true);

        // Action buttons
        Button editBtn = new Button("Edit");
        Button saveBtn = new Button("Save");
        Button backBtn = new Button("Back");
        Button logoutBtn = new Button("Logout");

        saveBtn.setDisable(true);

        // Enable editing mode
        editBtn.setOnAction(e -> {
            name.setDisable(false);
            contact.setDisable(false);
            email.setDisable(false);
            saveBtn.setDisable(false);
        });

        // Save changes (currently only in memory - not saved to DB yet)
        saveBtn.setOnAction(e -> {
            nameValue = name.getText();
            contactValue = contact.getText();
            emailValue = email.getText();

            name.setDisable(true);
            contact.setDisable(true);
            email.setDisable(true);
            saveBtn.setDisable(true);
        });

        // Back to dashboard
        backBtn.setOnAction(e -> new CustomerDashboard().show(stage, nameValue));

        // Logout and return to login screen
        logoutBtn.setOnAction(e -> new LoginScreen().show(stage));

        // Style logout button
        logoutBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        content.getChildren().addAll(
                title,
                new Label("Name:"), name,
                new Label("Contact:"), contact,
                new Label("Email:"), email,
                editBtn, saveBtn,
                backBtn,
                logoutBtn
        );

        // Bottom Navigation
        HBox nav = new HBox(10);
        nav.setPadding(new Insets(10));

        Button homeBtn = new Button("Home");
        Button bookingBtn = new Button("Bookings");
        Button messageBtn = new Button("Messages");
        Button profileBtn = new Button("Profile");

        homeBtn.setOnAction(e -> new CustomerDashboard().show(stage, nameValue));
        bookingBtn.setOnAction(e -> new CustomerBookings().show(stage, nameValue));
        messageBtn.setOnAction(e -> new CustomerMessages().show(stage, nameValue));
        profileBtn.setOnAction(e -> show(stage, nameValue));

        nav.getChildren().addAll(homeBtn, bookingBtn, messageBtn, profileBtn);

        // Main Layout
        BorderPane root = new BorderPane();
        root.setCenter(content);
        root.setBottom(nav);

        stage.setScene(new Scene(root, 350, 450));
    }
}