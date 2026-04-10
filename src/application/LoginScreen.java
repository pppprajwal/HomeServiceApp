package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Login Screen for the Home Service App
 * Allows users (Customer or Provider) to log in with username, password and role
 */
public class LoginScreen {

    public void show(Stage stage) {

        // Input fields
        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        // Role selection (Customer or Provider)
        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton customer = new RadioButton("Customer");
        customer.setToggleGroup(roleGroup);
        RadioButton provider = new RadioButton("Provider");
        provider.setToggleGroup(roleGroup);

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");

        Label status = new Label();

        // Login button logic
        loginBtn.setOnAction(e -> {

            // Step 1: Check if all fields are filled
            if (username.getText().isEmpty() ||
                password.getText().isEmpty() ||
                roleGroup.getSelectedToggle() == null) {

                status.setText("Fill all fields");
                return;
            }

            String enteredUsername = username.getText().trim();
            String enteredPassword = password.getText().trim();
            String selectedRole = ((RadioButton) roleGroup.getSelectedToggle()).getText();

            // Step 2: Validate username and password from database
            boolean valid = UserDAO.loginUser(enteredUsername, enteredPassword);

            if (!valid) {
                status.setText(" Invalid username or password!");
                return;
            }

            // Step 3: Check if selected role matches the role in database
            String roleInDB = UserDAO.getRole(enteredUsername);

            if (!roleInDB.equalsIgnoreCase(selectedRole)) {
                status.setText(" Role does not match registration!");
                return;
            }

            // Step 4: Login successful → open appropriate dashboard
            status.setText(" Login successful!");

            if (roleInDB.equalsIgnoreCase("Customer")) {
                new CustomerDashboard().show(stage, enteredUsername);
            } else if (roleInDB.equalsIgnoreCase("Provider")) {
                new ProviderDashboard().show(stage, enteredUsername);
            }
        });

        // Open registration screen
        registerBtn.setOnAction(e -> new RegisterScreen().show(stage));

        // Layout setup
        VBox layout = new VBox(10,
                new Label("Login"),
                username,
                password,
                new Label("Select Role"),
                customer,
                provider,
                loginBtn,
                registerBtn,
                status);

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 300, 350));
        stage.show();
    }
}