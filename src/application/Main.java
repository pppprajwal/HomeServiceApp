package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry point of the Home Service Booking Application
 * This is the starting class that JavaFX runs first
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Start the application by showing the Login Screen
        LoginScreen login = new LoginScreen();
        login.show(stage);
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}