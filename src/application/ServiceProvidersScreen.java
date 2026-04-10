package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Service Providers Screen
 * Displays list of providers for a selected service category
 */
public class ServiceProvidersScreen {

    /**
     * Shows providers for a specific category (e.g., Plumbing)
     * @param customerUsername - logged in customer's username (passed for booking)
     */
    public void show(Stage stage, String category, String customerUsername) {

        Label title = new Label(category + " Providers");

        ListView<String> list = new ListView<>();

        // Currently hardcoded provider names (can be replaced with database later)
        list.getItems().addAll("Ram Bahadur", "Sita Kumari");

        Button viewProfile = new Button("View Profile");
        Button back = new Button("Back");

        // Open provider profile when a provider is selected
        viewProfile.setOnAction(e -> {
            String selected = list.getSelectionModel().getSelectedItem();
            if (selected != null) {
                new ProviderProfileScreen().show(stage, selected, category, customerUsername);
            }
        });

        back.setOnAction(e -> new CustomerDashboard().show(stage, customerUsername));

        VBox layout = new VBox(10, title, list, viewProfile, back);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 350, 400));
    }
}