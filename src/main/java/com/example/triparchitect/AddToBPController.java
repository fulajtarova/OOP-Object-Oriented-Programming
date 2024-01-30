package com.example.triparchitect;

import com.example.triparchitect.decorator.Item;
import com.example.triparchitect.decorator.ItemDecorator;
import com.example.triparchitect.decorator.PersonalizedItem;
import com.example.triparchitect.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

/**
 * Controller class for adding personalized items to the user backpack.
 */
public class AddToBPController {

    /**
     * The User
     */
    private User user;
    /**
     * The Price
     */
    private int price = 0;

    @FXML
    private Label budgetLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField itemTextField;
    @FXML
    private Button priceButton;
    @FXML
    private Label priceLabel;
    @FXML
    private Button backButton;
    @FXML
    private Label outputLabel;

    /**
     * Initializing buttons for the user to interact with.
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
     * Sets user and budget label.
     *
     * @param user the user
     */
    public void set(User user) {
        this.user = user;
        setBudgetLabel();
    }

    /**
     * Sets budget label with the user budget.
     */
    public void setBudgetLabel() {
        int budget = user.getBudget();
        budgetLabel.setText("Budget (€): " + budget);
    }

    /**
     * Generate random price for the item and display it.
     */
    public void generatePrice() {
        Random rand = new Random();
        int min = 5;
        int max = 25;
        price = rand.nextInt((max - min) + 1) + min;
        priceLabel.setText("Price (€): " + price);
    }


    /**
     * Handle cases when the user clicks the confirm button, displaying the appropriate message, updating the user backpack and budget.
     * Serializes the user object and calls the showHomeWindow method.
     *
     * @throws IOException when user data cannot be saved
     */
    private void handleConfirm() throws IOException {
        String itemDescription = itemTextField.getText();

        if (0 == price || itemDescription.isEmpty()) {
            outputLabel.setText("Please fill in all fields.");
            return;
        } else if ((user.getBudget() - price) < 0) {
            outputLabel.setText("You don't have got enough money.");
            return;
        }
        // Create a base Item object with the given description and price
        Item baseItem = new Item(itemDescription, price);
        // Apply the Decorator pattern by wrapping the baseItem in a PersonalizedItem decorator
        ItemDecorator item = new PersonalizedItem(baseItem);

        // Aggregation: The User object contains a collection of backpack items
        user.addToBackpack(item.getDescription());
        user.deductTripCost(item.getPrice());

        try {
            SerializationUtils.serializeUser(user);
        } catch (IOException e) {
            throw new RuntimeException("Error: Unable to save user data.", e);
        }

        showHomeWindow();
    }

    /**
     * Show a home window and close the current window.
     *
     * @throws IOException when a home window cannot be loaded
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

        Stage editStage = (Stage) confirmButton.getScene().getWindow();
        editStage.close();
    }
}
