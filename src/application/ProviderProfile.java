package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Provider Profile Screen
 * Allows provider to view and edit their profile details
 */
public class ProviderProfile {

    private String nameValue;
    private String aboutValue = "Experienced service provider";
    private String experienceValue = "5 years";
    private String contactValue = "98XXXXXXXX";
    private String ratingValue = "4.5 / 5";   // Rating is fixed and cannot be edited

    public void show(Stage stage, String username) {

        nameValue = username;

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label title = new Label("Provider Profile");

        // Profile fields
        TextField name = new TextField(nameValue);
        TextField rating = new TextField(ratingValue);
        TextField about = new TextField(aboutValue);
        TextField experience = new TextField(experienceValue);
        TextField contact = new TextField(contactValue);

        // Initially disable editing
        name.setDisable(true);
        rating.setDisable(true);   // Rating stays locked
        about.setDisable(true);
        experience.setDisable(true);
        contact.setDisable(true);

        // Action buttons
        Button edit = new Button("Edit");
        Button save = new Button("Save");
        Button logout = new Button("Logout");

        save.setDisable(true);

        // Enable editing (except rating)
        edit.setOnAction(e -> {
            name.setDisable(false);
            about.setDisable(false);
            experience.setDisable(false);
            contact.setDisable(false);

            rating.setDisable(true); // rating remains disabled

            save.setDisable(false);
        });

        // Save changes (currently only in memory)
        save.setOnAction(e -> {
            nameValue = name.getText();
            aboutValue = about.getText();
            experienceValue = experience.getText();
            contactValue = contact.getText();

            name.setDisable(true);
            about.setDisable(true);
            experience.setDisable(true);
            contact.setDisable(true);

            rating.setDisable(true);
            save.setDisable(true);
        });

        // Logout button
        logout.setOnAction(e -> new LoginScreen().show(stage));

        content.getChildren().addAll(
                title,
                new Label("Name"), name,
                new Label("Rating"), rating,
                new Label("About"), about,
                new Label("Experience"), experience,
                new Label("Contact"), contact,
                edit, save, logout
        );

        BorderPane root = new BorderPane();
        root.setCenter(content);

        stage.setScene(new Scene(root, 350, 450));
    }
}