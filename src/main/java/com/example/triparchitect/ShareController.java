package com.example.triparchitect;

import com.example.triparchitect.users.Share;
import com.example.triparchitect.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Controller class for sharing a trip with another user. The user can choose a trip and a user to share it with.
 */
public class ShareController {
    /**
     * The User.
     */
    private User user;
    /**
     * The Selected index of the trip so that we can share it correctly.
     */
    private int selectedIndex;

    @FXML
    private Button confirmButton;
    @FXML
    private Label previousDateLabel;
    @FXML
    private ChoiceBox<String> shareComboBox;
    @FXML
    private ChoiceBox<String> tripComboBox;
    @FXML
    private Button backButton;

    /**
     * Initializing buttons.
     */
    @FXML
    public void initialize() {
        // Event handling using lambda expressions
        confirmButton.setOnAction(event -> {
            try {
                handleConfirm();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        backButton.setOnAction(event -> {
            try {
                showHomeWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sets user and fills the trip and user ComboBoxes.
     *
     * @param user the user
     */
    public void set(User user) {
        this.user = user;
        fillTripComboBox();
        fillUserComboBox();
    }

    /**
     * Fill trip ComboBox with the user's trips and dates so that the user can choose which trip to share.
     */
    private void fillTripComboBox() {
        tripComboBox.getItems().clear();

        List<String> trips = user.getMyTrips();
        List<String> dates = user.getDates();

        tripComboBox.getItems().addAll(trips);

        // Event listener for trip ComboBox selection
        tripComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            selectedIndex = tripComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                previousDateLabel.setText("Date: " + dates.get(selectedIndex));
            }
            confirmButton.setDisable(newValue == null);
        });
    }

    /**
     * Fill user ComboBox with all the users so that the user can choose which user to share the trip with.
     */
    private void fillUserComboBox() {
        shareComboBox.getItems().clear();

        // Reading from a file, an example of abstraction
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String username = parts[0];
                    if (!username.equals(user.getUsername())) {
                        shareComboBox.getItems().add(username);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading credentials.txt: " + e.getMessage());
        }
    }

    /**
     * Handle the confirmation button. Share the trip with the selected user and update it in the database.
     * Also check if the trip has already been shared with the selected user and if it has, don't share it again.
     * After sharing the trip, open the home window.
     *
     * @throws IOException the io exception
     */
    private void handleConfirm() throws IOException {
        if (shareComboBox.getValue() == null || tripComboBox.getValue() == null) return;

        String selectedUser = shareComboBox.getValue();
        String selectedTrip = tripComboBox.getValue();
        String tripDate = user.getDates().get(selectedIndex);

        // Create or load the Share object
        // Using SerializationUtils class to deserialized Share, an example of abstraction
        Share sharedTrips;
        try {
            sharedTrips = SerializationUtils.deserializeShare();
        } catch (IOException | ClassNotFoundException e) {
            sharedTrips = new Share(); // Creating a new Share object, an example of encapsulation
        }

        // Check if the same trip has already been shared with the selected user
        boolean isDuplicate = false;
        List<String> toUsers = sharedTrips.getTo();
        List<String> fromUsers = sharedTrips.getFrom();
        List<String> trips = sharedTrips.getTrip();
        List<String> dates = sharedTrips.getDate();

        for (int i = 0; i < toUsers.size(); i++) {
            if (toUsers.get(i) != null && toUsers.get(i).equals(selectedUser) && fromUsers.get(i) != null && fromUsers.get(i).equals(user.getUsername()) && trips.get(i) != null && trips.get(i).equals(selectedTrip) && dates.get(i) != null && dates.get(i).equals(tripDate)) {
                isDuplicate = true;
                break;
            }
        }


        if (isDuplicate) {
            System.out.println("This invitation has already been created.");
        } else {
            // Method invocation, an example of encapsulation and abstraction
            sharedTrips.addShare(selectedUser, user.getUsername(), selectedTrip, tripDate);

            // Using SerializationUtils class to serialize Share, an example of abstraction
            SerializationUtils.serializeShare(sharedTrips);
        }

        showHomeWindow();
    }


    /**
     * Show a home window.
     *
     * @throws IOException the io exception
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