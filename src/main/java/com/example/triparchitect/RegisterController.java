package com.example.triparchitect;

import com.example.triparchitect.exceptions.YouCantBeSenior;
import com.example.triparchitect.exceptions.YouCantBeStudent;
import com.example.triparchitect.exceptions.YouCantBeStudentAndSenior;
import com.example.triparchitect.users.Senior;
import com.example.triparchitect.users.Student;
import com.example.triparchitect.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Register controller.
 */
public class RegisterController {
    /**
     * The User type.
     */
    User.UserType userType;
    /**
     * The User.
     */
    private User user;
    @FXML
    private TextField budgetTextField;
    @FXML
    private Button confirmButton;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label outputLabel;
    @FXML
    private CheckBox studentCheckBox;
    @FXML
    private CheckBox seniorCheckBox;
    @FXML
    private TextField ageTextField;
    @FXML
    private Label usernameLabel;

    /**
     * Initializing combo box and confirm button.
     */
    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> handleConfirm()); // Event handling using lambda expressions
    }


    /**
     * Sets user, username label and fills combo box.
     *
     * @param user the user
     */
    public void set(User user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());
        fillComboBox();
    }

    /**
     * Filling combo box with user types.
     */
    private void fillComboBox() {
        comboBox.getItems().addAll("Adventure seeker", "Culture explorer", "Nature lover", "Foodie");
    }

    /**
     * Handle confirm, setting the user type and budget.
     * Creating and adding an observer to the user
     * Creating different user types based on the user's selection
     * Handling exceptions by displaying an error message
     */
    private void handleConfirm() {
        // Creating and adding an observer to the user, demonstrating the Observer pattern
        User.UserObserver userNotification = new User.UserNotification();
        user.addObserver(userNotification);

        String selectedUserType = comboBox.getValue();
        String budgetText = budgetTextField.getText();
        boolean isStudent = studentCheckBox.isSelected();
        boolean isSenior = seniorCheckBox.isSelected();

        String ageText = ageTextField.getText();

        if (selectedUserType == null || budgetText.isEmpty() || ageText.isEmpty()) {
            outputLabel.setText("Please fill in all fields and select a user type.");
            return;
        }

        int budget;
        int age;

        try {
            budget = Integer.parseInt(budgetText);
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            outputLabel.setText("Invalid input for budget or age.");
            return;
        }

        // Using the Factory pattern to create a user type object
        switch (comboBox.getValue()) {
            case "Adventure seeker" -> userType = new User.AdventureSeeker();
            case "Culture explorer" -> userType = new User.CultureExplorer();
            case "Nature lover" -> userType = new User.NatureLover();
            case "Foodie" -> userType = new User.Foodie();
        }


        // Updating the user object and printing the user type
        user.updateUser(budget, age, userType);
        user.getUserType().printUserType();


        // Handling exceptions with custom exception classes
        // exception for choosing both student and senior
        try {
            if (isStudent && isSenior) {
                throw new YouCantBeStudentAndSenior("You can't be a student and senior \n at the same time.");
            }
        } catch (YouCantBeStudentAndSenior e) {
            outputLabel.setText(e.getMessage());
            return;
        }

        // exception for choosing a student
        try {
            if (isStudent) {
                if (age > 26) {
                    throw new YouCantBeStudent("You can't be a student if you are older than 26.");
                }
                user = new Student(user);
            }
        } catch (YouCantBeStudent e) {
            outputLabel.setText(e.getMessage());
            return;
        }

        // exception for choosing senior
        try {
            if (isSenior) {
                if (age < 60) {
                    throw new YouCantBeSenior("You can't be a senior if you are younger than 60.");
                }
                user = new Senior(user);
            }
        } catch (YouCantBeSenior e) {
            outputLabel.setText(e.getMessage());
            return;
        }

        // Using SerializationUtils to serialize the user object, demonstrating abstraction
        try {
            SerializationUtils.serializeUser(user);
            showHomeWindow();
        } catch (IOException e) {
            outputLabel.setText("Error: Unable to save user data.");
        }
    }


    /**
     * @throws IOException when the home window can't be loaded
     */
    private void showHomeWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        HomeController home = loader.getController();
        home.set(user);

        Stage homeStage = new Stage();
        homeStage.setTitle("Home");
        homeStage.setScene(new Scene(root));
        homeStage.show();

        Stage userTypeStage = (Stage) confirmButton.getScene().getWindow();
        userTypeStage.close();
    }
}