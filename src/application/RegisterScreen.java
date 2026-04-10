package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Registration Screen
 * Allows new users to create an account as Customer or Provider
 */
public class RegisterScreen {

    public void show(Stage stage) {

        // Input fields
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField locationField = new TextField();

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        locationField.setPromptText("Location (e.g., Buddhanagar)");

        // Role selection
        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton customer = new RadioButton("Customer");
        customer.setToggleGroup(roleGroup);
        RadioButton provider = new RadioButton("Provider");
        provider.setToggleGroup(roleGroup);

        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back");

        Label status = new Label();

        // Register button logic
        registerBtn.setOnAction(e -> {

            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String location = locationField.getText().trim();

            // Check if all required fields are filled
            if (username.isEmpty() || password.isEmpty() || location.isEmpty() || roleGroup.getSelectedToggle() == null) {
                status.setText("❌ Fill all fields");
                return;
            }

            String role = ((RadioButton) roleGroup.getSelectedToggle()).getText();

            // Save user to database
            boolean success = UserDAO.registerUser(username, password, role, location);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("✅ Registered Successfully!");
                alert.showAndWait();

                // Redirect to login screen after successful registration
                new LoginScreen().show(stage);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("❌ Registration failed! Username may already exist.");
                alert.showAndWait();
            }
        });

        // Back to login screen
        backBtn.setOnAction(e -> new LoginScreen().show(stage));

        // Layout
        VBox layout = new VBox(10,
                new Label("Register"),
                usernameField,
                passwordField,
                new Label("Select Role"),
                customer,
                provider,
                locationField,
                registerBtn,
                backBtn,
                status);

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 350, 400));
        stage.show();
    }
}