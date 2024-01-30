package com.example.triparchitect;

import com.example.triparchitect.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

/**
 * Controller class for creating a personalized trip for the user. The user can choose a date, a name and a price for the trip.
 */
public class CreateTripController {
    /**
     * The User.
     */
    private User user;
    /**
     * The Price.
     */
    private int price = 0;

    @FXML
    private Button backButton;
    @FXML
    private Label budgetLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button priceButton;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField tripNameTextField;
    @FXML
    private Label outputLabel;

    /**
     * Initialize buttons for the user to interact with.
     */
    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            try {
                handleConfirm();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        priceButton.setOnAction(event -> generatePrice());

        backButton.setOnAction(event -> {
            try {
                showHomeWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    /**
     * Sets user and labels.
     *
     * @param user the user
     */
    public void set(User user) {
        this.user = user;
        setBudgetLabel();
    }

    /**
     * Sets budget label with the user's budget.
     */
    public void setBudgetLabel() {
        int budget = user.getBudget();
        budgetLabel.setText("Budget (€): " + budget);
    }

    /**
     * Generate price for the trip.
     */
    public void generatePrice() {
        Random rand = new Random();
        int min = 30;
        int max = 60;
        price = rand.nextInt((max - min) + 1) + min;
        priceLabel.setText("Price (€): " + price);
    }

    /**
     * Handles the confirm button. Checks if all fields are filled in and if the user has enough money. If so, the trip is added to the user's trips.
     * The user's budget is updated and the user is redirected to the home window and the car animation window is displayed.
     *
     * @throws IOException when it's unable to save user data
     */
    private void handleConfirm() throws IOException {
        LocalDate selectedDate = datePicker.getValue();
        String trip = tripNameTextField.getText();

        if (0 == price || trip == null || selectedDate == null) {
            outputLabel.setText("Please fill in all fields.");
            return;
        } else if ((user.getBudget() - price) < 0) {
            outputLabel.setText("You don't have got enough money.");
            return;
        }

        user.addToTrips(trip + " - €" + price);
        user.addToDates(String.valueOf(selectedDate));
        user.deductTripCost(price);

        try {
            SerializationUtils.serializeUser(user);
        } catch (IOException e) {
            throw new RuntimeException("Error: Unable to save user data.", e);
        }

        showHomeWindow();
        openCarAnimationWindow();
    }


    /**
     * Shows the home window and closes the current window.
     *
     * @throws IOException when it's unable to load the home window
     */
    private void showHomeWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        HomeController home = loader.getController();
        home.set(user);

        Stage homeStage = new Stage();
        homeStage.setTitle("Home");
        homeStage.setScene(new Scene(root, 750, 600));
        homeStage.show();

        Stage editStage = (Stage) confirmButton.getScene().getWindow();
        editStage.close();
    }

    /**
     * Opens the car animation window.
     *
     * @throws IOException when it's unable to load the car animation window
     */
    private void openCarAnimationWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("car.fxml"));
        Parent root = loader.load();

        Stage animationStage = new Stage();

        animationStage.setTitle("Car Animation");
        animationStage.setScene(new Scene(root));
        animationStage.showAndWait();
    }

}
