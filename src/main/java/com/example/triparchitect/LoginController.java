package com.example.triparchitect;

import com.example.triparchitect.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controller class for handling user login.
 */
public class LoginController {
    /**
     * The Credentials file stores the username and password of the user.
     */
    private final String credentialsFile = "credentials.txt";
    /**
     * The User.
     */
    private User user;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label outputLabel;

    /**
     * Initialize button actions.
     */
    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());
    }

    /**
     * Update the label with an error message.
     *
     * @param message the message
     */
    private void updateOutputLabel(String message) {
        outputLabel.setText(message);
    }

    /**
     * Method for handling the login button, checks if the user exists and logs them in,
     * otherwise displays a message to further register or wrong username/password.
     * After the user logs in, the method calls the showHomeWindow method.
     */
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            updateOutputLabel("Please enter both username and password.");
            return;
        }

        // Reading the credentials file and checking if the user exists
        try {
            boolean userFound = false;
            boolean userRegistered = false;

            List<String> lines = Files.readAllLines(Paths.get(credentialsFile));
            for (String line : lines) {
                String[] fields = line.split(";");
                if (username.equals(fields[0])) {
                    userRegistered = true;
                    if (password.equals(fields[1])) {
                        // Deserialize a user object and check if the user is found
                        try {
                            user = SerializationUtils.deserializeUser(username);

                            updateOutputLabel("Login Successful");
                            System.out.println(user.WelcomeMessage());
                            showHomeWindow();
                            userFound = true;
                            break;
                        } catch (IOException | ClassNotFoundException e) {
                            updateOutputLabel("Error: \nUnable to read user data.");
                            userFound = true;
                            break;
                        }
                    }
                }
            }

            if (!userFound && userRegistered) {
                updateOutputLabel("Login Failed:\nInvalid password.");
            } else if (!userFound) {
                updateOutputLabel("Login Failed:\nUser was not registered.");
            }

        } catch (IOException e) {
            updateOutputLabel("Error: \nUnable to read user data.");
        }
    }

    /**
     * Method for registering a new user, checks if the user exists and registers them, otherwise displays a message.
     * After the user registers, the method calls the showRegistrationWindow method.
     */
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if the user exists and register a new user if they do not, demonstrating file I/O
        try {
            List<String> lines = Files.readAllLines(Paths.get(credentialsFile));

            boolean userExists = false;
            for (String line : lines) {
                String[] credentials = line.split(";");
                if (username.equals(credentials[0])) {
                    userExists = true;
                    break;
                }
            }

            if (userExists) {
                updateOutputLabel("Registration Failed:\n User is already registered.");
            } else {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFile, true))) {
                    writer.write(username + ";" + password + "\n");
                    updateOutputLabel("Registration Successful");

                    // Create a new User object with the provided username and password
                    user = new User(username, password);

                    // Serialize user object
                    SerializationUtils.serializeUser(user);
                    showRegistrationWindow();

                } catch (IOException e) {
                    updateOutputLabel("Error: \nUnable to register user.");
                }
            }
        } catch (IOException e) {
            updateOutputLabel("Error: \nUnable to read credentials file.");
        }
    }


    /**
     * Show a home window.
     */
    private void showHomeWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();

            HomeController home = loader.getController();
            home.set(user);

            Stage homeStage = new Stage();
            homeStage.setTitle("Home");
            homeStage.setScene(new Scene(root, 750, 600));
            homeStage.show();

            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            updateOutputLabel("Error: Unable to open home window.");
        }
    }

    /**
     * show a registration window.
     */
    private void showRegistrationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();

            RegisterController userTypeController = loader.getController();
            userTypeController.set(user);

            Stage homeStage = new Stage();
            homeStage.setTitle("Registration");
            homeStage.setScene(new Scene(root));
            homeStage.show();

            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            updateOutputLabel("Error: Unable to open home window.");
        }
    }
}