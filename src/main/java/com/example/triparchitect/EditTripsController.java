package com.example.triparchitect;

import com.example.triparchitect.users.Share;
import com.example.triparchitect.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * Controller class for editing the user's trips. The user can change the date of the trip, delete the trip or add a near trip.
 */
public class EditTripsController {
    /**
     * The Selected index of the trip so that we can update it correctly.
     */
    int selectedIndex;
    /**
     * The Random activity that user can add to his trip.
     */
    String randomActivity;
    /**
     * The Random activity price.
     */
    int randomActivityPrice = 0;
    /**
     * The User.
     */
    private User user;
    @FXML
    private Button confirmButton;
    @FXML
    private CheckBox deleteCheckBox;
    @FXML
    private Label previousDateLabel;
    @FXML
    private ChoiceBox<String> tripComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private CheckBox nearTripCheckBox;
    @FXML
    private Label nearTripLabel;
    @FXML
    private Label budgetLabel;
    @FXML
    private Button backButton;

    /**
     * Initialize buttons that are used in this window.
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

        backButton.setOnAction(event -> {
            try {
                showHomeWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sets user, fills the combobox with the user's trips and sets the near trip label and budget label.
     *
     * @param user the user
     */
    public void set(User user) {
        this.user = user;
        fillComboBox();
        setNearTripLabel();
        setBudgetLabel();
    }

    /**
     * Sets budget label.
     */
    public void setBudgetLabel() {
        int budget = user.getBudget();
        budgetLabel.setText("Budget (€): " + budget);
    }

    /**
     * Sets near trip with random activity and price.
     */
    private void setNearTripLabel() {
        String[] activities = {"geocaching hunt", "attend workshops", "take a bike ride", "photography tour", "fitness class in the park", "visit local parks", "go for a hike", "visit a local art fair", "go birdwatching", "go for a swim in a lake", "visit planetarium", "block party", "haunted house tour", "explore street markets"};

        Random rand = new Random();
        int index = rand.nextInt(activities.length);
        randomActivity = activities[index];

        Random rand2 = new Random();
        int min = 5;
        int max = 15;

        randomActivityPrice = rand2.nextInt((max - min) + 1) + min;

        nearTripLabel.setText(randomActivity + " - €" + randomActivityPrice);
    }

    /**
     * Fill combobox with user's trips so that he can choose which trip he wants to edit.
     */
    private void fillComboBox() {
        tripComboBox.getItems().clear();
        boolean deleting = deleteCheckBox.isSelected();

        List<String> trips = user.getMyTrips();
        List<String> dates = user.getDates();

        tripComboBox.getItems().addAll(trips);


        tripComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            selectedIndex = tripComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                previousDateLabel.setText("Previous Date: " + dates.get(selectedIndex));
            }
            confirmButton.setDisable(newValue == null && !deleting);
        });
    }

    /**
     * Handle confirm. When user clicks confirm, the program checks if the user wants to delete the trip, add a near trip or change the date of the trip.
     * Those changes are saved to the user's trips and dates.
     *
     * @throws IOException the io exception
     */
    private void handleConfirm() throws IOException {
        List<String> trips = user.getMyTrips();
        List<String> dates = user.getDates();
        LocalDate selectedDate = datePicker.getValue();

        String originalTrip = trips.get(selectedIndex);
        String originalDate = dates.get(selectedIndex);

        //delete a whole trip
        if (deleteCheckBox.isSelected()) {
            trips.remove(selectedIndex);
            dates.remove(selectedIndex);
            updateSharedTrips(originalTrip, originalDate, null, null);
        }
        //when user doesn't have enough money for a trip
        else if (nearTripCheckBox.isSelected() && (user.getBudget() - randomActivityPrice) < 0) {
            System.out.println("You dont have got enough money.\n");
            return;
        }
        //when user changes date and adds a new trip
        else if (selectedDate != null) {
            if (nearTripCheckBox.isSelected() && (user.getBudget() - randomActivityPrice) > 0) {
                user.addToTrips(randomActivity + " - €" + randomActivityPrice);
                user.addToDates(String.valueOf(selectedDate));
                user.deductTripCost(randomActivityPrice);
            }
            updateSharedTrips(originalTrip, originalDate, originalTrip, selectedDate.toString());
            dates.set(selectedIndex, selectedDate.toString());

        }
        //when user only adds a new trip
        else if (selectedDate == null && nearTripCheckBox.isSelected() && (user.getBudget() - randomActivityPrice) > 0) {
            user.addToTrips(randomActivity + " - €" + randomActivityPrice);
            user.addToDates(dates.get(selectedIndex));
            user.deductTripCost(randomActivityPrice);
        }

        try {
            SerializationUtils.serializeUser(user);
        } catch (IOException e) {
            throw new RuntimeException("Error: Unable to save user data.", e);
        }

        showHomeWindow();
    }


    /**
     * When a user changes the date of a trip or deletes a trip,
     * the program updates the shared trips so that the other users can see the changes
     * and those trips are actual.
     * All the changes are saved to the shared trips.
     *
     * @param originalTrip information about an original trip
     * @param originalDate original date of a selected trip
     * @param updatedTrip  information about an updated trip
     * @param updatedDate  updated date of a selected trip
     */
    private void updateSharedTrips(String originalTrip, String originalDate, String updatedTrip, String updatedDate) {
        try {
            Share sharedTrips = SerializationUtils.deserializeShare();
            List<String> toUsers = sharedTrips.getTo();
            List<String> fromUsers = sharedTrips.getFrom();
            List<String> trips = sharedTrips.getTrip();
            List<String> dates = sharedTrips.getDate();

            for (int i = 0; i < toUsers.size(); i++) {
                if (fromUsers.get(i) != null && fromUsers.get(i).equals(user.getUsername()) && trips.get(i) != null && trips.get(i).equals(originalTrip) && dates.get(i) != null && dates.get(i).equals(originalDate)) {

                    if (updatedTrip == null || updatedDate == null) {
                        // Remove the shared trip if the updated trip or date is null (trip deleted)
                        toUsers.remove(i);
                        fromUsers.remove(i);
                        trips.remove(i);
                        dates.remove(i);
                        i--; // Decrement the index to account for the removed element
                    } else {
                        // Update the shared trip with the new trip and date
                        trips.set(i, updatedTrip);
                        dates.set(i, updatedDate);
                    }
                }
            }

            // Save the updated shared trips
            SerializationUtils.serializeShare(sharedTrips);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error: Unable to update shared trips.", e);
        }
    }

    /**
     * Shows a home window.
     *
     * @throws IOException when home.fxml is not found
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

