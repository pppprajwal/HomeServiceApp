package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Provider Profile Screen (viewed by Customer)
 * Shows details of a service provider and allows the customer to book them.
 */
public class ProviderProfileScreen {

    /**
     * Displays provider information and booking option
     * @param name              - Provider's name
     * @param category          - Service category
     * @param loggedInCustomer  - Username of the customer who is viewing
     */
    public void show(Stage stage, String name, String category, String loggedInCustomer) {

        Label providerName = new Label("Name: " + name);
        Label about = new Label("About: Experienced " + category + " service provider.");
        Label reviews = new Label("Reviews: ★★★★☆ (Good Service)");
        Label pricing = new Label("Starting Price: NPR 500");

        Button bookNow = new Button("Book Now");
        Button back = new Button("Back");

        // Open booking screen when "Book Now" is clicked
        bookNow.setOnAction(e -> {
            new BookingScreen().show(stage, name, category, 500, loggedInCustomer);
        });

        // Go back to service providers list
        back.setOnAction(e -> new ServiceProvidersScreen().show(stage, category, loggedInCustomer));

        VBox layout = new VBox(10, providerName, about, reviews, pricing, bookNow, back);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 350, 400));
    }
}