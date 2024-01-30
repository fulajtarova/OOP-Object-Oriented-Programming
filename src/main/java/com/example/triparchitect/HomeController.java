package com.example.triparchitect;

import com.example.triparchitect.packages.Package;
import com.example.triparchitect.packages.PackageFactory;
import com.example.triparchitect.trips.Trip;
import com.example.triparchitect.trips.TripFactory;
import com.example.triparchitect.users.Senior;
import com.example.triparchitect.users.Share;
import com.example.triparchitect.users.Student;
import com.example.triparchitect.users.User;
import com.example.triparchitect.weather.Weather;
import com.example.triparchitect.weather.WeatherActivityFactory;
import com.example.triparchitect.weather.WeatherService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Controller class for the home page of the application.
 * The user can see his budget, benefit points, username, age, weather, backpack items and trips and shared trips from other users.
 * He can also create a new trip or new personalized trip, a new recommended activity, edit his trips, share his trips with other users and
 * add personalized items to his backpack
 */
public class HomeController {
    /**
     * Package factory for creating packages that are recommended to the user, and adds them to the backpack.
     */
    private final PackageFactory packageFactory;
    /**
     * Trip factory for creating trips that are recommended to the user
     */
    private final TripFactory tripFactory;
    /**
     * The User.
     */
    private User user;
    /**
     * The Random weather that will be shown to the user.
     */
    private Weather randomWeather;
    /**
     * The Weather service that multi-threads the weather.
     */
    private WeatherService weatherService;

    @FXML
    private Label budgetLabel;
    @FXML
    private Label benefitLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label usertypeLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label weatherLabel;
    @FXML
    private Label bpInfoLabel;
    @FXML
    private ComboBox<String> tripComboBox;
    @FXML
    private ComboBox<String> activityComboBox;
    @FXML
    private Button logoutButton;
    @FXML
    private Button newTripButton;
    @FXML
    private Button newRecTripButton;
    @FXML
    private TableView<CombinedTableEntry> combinedTable;
    @FXML
    private TableColumn<CombinedTableEntry, String> tripColumn;
    @FXML
    private TableColumn<CombinedTableEntry, String> bpColumn;
    @FXML
    private TableColumn<CombinedTableEntry, String> dateColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button editTripsButton;
    @FXML
    private Button addToBpButton;
    @FXML
    private TableColumn<CombinedTableEntry, String> friendsTripColumn;
    @FXML
    private Button shareButton;
    @FXML
    private Button createTripButton;

    /**
     * Instantiates a new Home controller.
     */
    public HomeController() {
        // Create a new PackageFactory object
        this.packageFactory = new PackageFactory();
        // Create a new TripFactory object
        this.tripFactory = new TripFactory();
        // Create a new WeatherService object
        this.weatherService = new WeatherService();
    }

    /**
     * Initializing buttons
     */
    @FXML
    public void initialize() {
        logoutButton.setOnAction(event -> {
            try {
                handleLogout();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        newTripButton.setOnAction(event -> {
            try {
                openCarAnimationWindow();
                addSelectedTrip();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        newRecTripButton.setOnAction(event -> addActivity());

        editTripsButton.setOnAction(event -> {
            try {
                openEditTripsWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        addToBpButton.setOnAction(event -> {
            try {
                openBPWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        shareButton.setOnAction(event -> {
            try {
                openShareWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        createTripButton.setOnAction(event -> {
            try {
                openCreateTripWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sets user, labels, table, combo boxes and weather.
     *
     * @param user the user
     */
    public void set(User user) {
        this.user = user;
        setUsername(user.getUsername());
        setUserTypeLabel();
        setBudgetLabel();
        setBenefitLabel();
        setAgeLabel();
        setBpInfoLabel();
        setCombinedTable();
        fillComboBox();
        fetchWeather();
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    /**
     * Sets user type label.
     */
    public void setUserTypeLabel() {
        String userType = user.getUserType().getUserTypeDescription();
        usertypeLabel.setText(userType);
    }

    /**
     * Sets budget label.
     */
    public void setBudgetLabel() {
        int budget = user.getBudget();
        budgetLabel.setText(String.valueOf(budget));
    }

    /**
     * Sets benefit label based on the user type if the user is a student or senior.
     */
    public void setBenefitLabel() {
        // Inheritance: Student and Senior classes extend the User class
        // Explicit use of RTTI with instanceof
        String Status = "None";
        if (user instanceof Student) {
            Status = "Student discount 20%";
        }

        if (user instanceof Senior) {
            Status = "Senior discount 30%";
        }
        benefitLabel.setText(Status);
    }

    /**
     * Sets age label.
     */
    public void setAgeLabel() {
        int userAge = user.getAge();
        ageLabel.setText(String.valueOf(userAge));
    }

    /**
     * Sets backpack info label, what contains package for the user.
     */
    private void setBpInfoLabel() {
        Optional<Package> userPackage = generatePackage();
        userPackage.ifPresent(pkg -> {
            if (!user.isBackpackInitialized()) {
                pkg.prepareBackpack();
                user.setBackpackInitialized(true);
            }
            bpInfoLabel.setText(pkg.getPackageDetails());

        });
    }

    /**
     * Method that generates package for the user based on his type.
     *
     * @return package for the user
     */
    private Optional<Package> generatePackage() {
        return packageFactory.createPackage(user);
    }

    /**
     * Sets table with users backpack, trips, date of the trips and shared trips.
     */
    private void setCombinedTable() {
        List<String> myTrips = user.getMyTrips();
        List<String> myBp = user.getBackpack();
        List<String> myDates = user.getDates();
        List<String> sharedTrips = getSharedTrips();

        ObservableList<CombinedTableEntry> tableData = FXCollections.observableArrayList();

        int maxSize = Math.max(myTrips.size(), myBp.size());
        for (int i = 0; i < maxSize; i++) {
            String trip = i < myTrips.size() ? myTrips.get(i) : "";
            String backpack = i < myBp.size() ? myBp.get(i) : "";
            String date = i < myDates.size() ? myDates.get(i) : "";
            String shared = i < sharedTrips.size() ? sharedTrips.get(i) : "";
            tableData.add(new CombinedTableEntry(trip, backpack, date, shared));
        }

        tripColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTrip()));
        bpColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBackpack()));
        dateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDate()));
        friendsTripColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSharedTrip()));


        combinedTable.setItems(tableData);
    }

    /**
     * Prepare a list of trips that has been shared with the user for the table.
     *
     * @return list of shared trips
     */
    //
    private List<String> getSharedTrips() {
        List<String> sharedTrips = new ArrayList<>();

        try {
            // Share class encapsulates shared trip data
            Share share = SerializationUtils.deserializeShare();

            List<String> toUsers = share.getTo();
            List<String> fromUsers = share.getFrom();
            List<String> trips = share.getTrip();
            List<String> dates = share.getDate();

            for (int i = 0; i < toUsers.size(); i++) {
                String toUser = toUsers.get(i);
                if (toUser != null && toUser.equals(user.getUsername())) {
                    String fromUser = fromUsers.get(i) != null ? fromUsers.get(i) : "";
                    String trip = trips.get(i) != null ? trips.get(i) : "";
                    String date = dates.get(i) != null ? dates.get(i) : "";
                    String sharedTripInfo = "From: " + fromUser + ", Trip: " + trip + ", Date: " + date;
                    sharedTrips.add(sharedTripInfo);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return sharedTrips;
    }

    /**
     * fill combo box with trips that user can afford
     */
    private void fillComboBox() {
        tripComboBox.getItems().clear();

        ResultHolder<?> tripResult = generateTrip();
        // Explicit use of RTTI with instanceof
        if (tripResult.result() instanceof Trip userTrip) {
            List<String> places = userTrip.getAffordablePlaces();
            List<Integer> prices = userTrip.getPrice();

            for (String place : places) {
                int index = userTrip.getPlaces().indexOf(place);
                String option = place + " - €" + prices.get(index);
                if (!user.getMyTrips().contains(option)) {
                    tripComboBox.getItems().add(option);
                }
            }
        } else {
            System.out.println("Error, trip couldn't be generated");
        }

        tripComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> newTripButton.setDisable((newValue == null || user.getBudget() < getTripCost(newValue))));
    }

    /**
     * Generate trips for the user.
     *
     * @return result holder with trips
     */
    private ResultHolder<?> generateTrip() {
        return tripFactory.createTrip(user);
    }

    /**
     * Displays the weather information through multithreading.
     */
    private void fetchWeather() {
        // Creating a Task to fetch weather data in the background (Multithreading)
        Task<Weather> weatherTask = new Task<>() {
            protected Weather call() {
                // WeatherService dependency is injected to fetch the weather information
                weatherService = new WeatherService();
                randomWeather = weatherService.getWeather();
                return randomWeather;
            }
        };

        // Updating UI when the weather data is fetched successfully (Multithreading)
        weatherTask.setOnSucceeded(event -> {
            Weather weather = (Weather) event.getSource().getValue();
            weatherLabel.setText("Current weather: " + weather);
            fillActivityComboBox();
        });

        // Starting a new thread to execute the weather task (Multithreading)
        Thread weatherThread = new Thread(weatherTask);
        weatherThread.setDaemon(true);
        weatherThread.start();
    }

    /**
     * Fill combo box with activities that are recommended for the user based on the weather.
     */
    private void fillActivityComboBox() {
        activityComboBox.getItems().clear();

        // WeatherActivityFactory is used to create appropriate trips based on weather conditions
        WeatherActivityFactory weatherTripFactory = new WeatherActivityFactory();
        List<String> recommendedTrips = weatherTripFactory.generateTripsByWeather(randomWeather);

        activityComboBox.getItems().addAll(recommendedTrips);

        activityComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> newRecTripButton.setDisable(newValue == null));
    }

    /**
     * Adding a selected trip to the user.
     * In case of student selecting cinema, movie tickets that were student bonus are removed from the backpack.
     */
    private void addSelectedTrip() {
        String selectedTrip = tripComboBox.getSelectionModel().getSelectedItem();
        if (selectedTrip != null) {
            int tripCost = (int) getTripCost(selectedTrip);

            if (user.getBudget() >= tripCost) {
                LocalDate selectedDate = datePicker.getValue();
                if (selectedDate != null) {

                    user.addToTrips(selectedTrip);
                    user.addToDates(String.valueOf(selectedDate));

                    tripComboBox.getItems().remove(selectedTrip);
                    setCombinedTable();

                    if (selectedTrip.equals("Cinema - €0")) {
                        user.removeItemFromBackpack("Movie tickets (student benefit)");
                        setCombinedTable();
                    }

                    user.deductTripCost(tripCost);
                    setBudgetLabel();
                }
            }
        }
        try {
            SerializationUtils.serializeUser(user);
        } catch (IOException e) {
            throw new RuntimeException("Error: Unable to save user data.", e);
        }
    }

    /**
     * Get the cost of the trip from the combo box.
     *
     * @param selectedTrip selected trip
     * @return cost of the trip
     */
    private double getTripCost(String selectedTrip) {
        String[] parts = selectedTrip.split(" - €");
        return Integer.parseInt(parts[1]);
    }


    /**
     * Adding a selected activity to the user trips and serializing the user.
     */
    private void addActivity() {
        String selectedTrip = activityComboBox.getSelectionModel().getSelectedItem();
        if (selectedTrip != null) {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                user.addToTrips(selectedTrip);
                user.addToDates(String.valueOf(selectedDate));
                activityComboBox.getItems().remove(selectedTrip);
                setCombinedTable();
            }
        }

        try {
            SerializationUtils.serializeUser(user);
        } catch (IOException e) {
            throw new RuntimeException("Error: Unable to save user data.", e);
        }
    }


    /**
     * Serializes the user and open login window.
     *
     * @throws IOException the io exception
     */
    private void handleLogout() throws IOException {
        System.out.println(user.GoodbyeMessage());
        try {
            SerializationUtils.serializeUser(user);
        } catch (IOException e) {
            throw new RuntimeException("Error: Unable to save user data.", e);
        }
        showLoginWindow();
    }

    /**
     * Open login window and close home window after logout.
     *
     * @throws IOException the io exception
     */
    private void showLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(root));
        loginStage.show();

        Stage homeStage = (Stage) logoutButton.getScene().getWindow();
        homeStage.close();
    }

    /**
     * Open edit trips window after clicking edit trips button.
     *
     * @throws IOException the io exception
     */
    private void openEditTripsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_trips.fxml"));
        Parent editTripsRoot = loader.load();
        EditTripsController editTripsController = loader.getController();
        editTripsController.set(user);

        Stage stage = new Stage();
        stage.setTitle("Edit Trips");
        stage.setScene(new Scene(editTripsRoot));
        stage.show();

        Stage homeStage = (Stage) editTripsButton.getScene().getWindow();
        homeStage.close();
    }

    /**
     * Open the backpack window after clicking addtobpbutton button.
     *
     * @throws IOException the io exception
     */
    private void openBPWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bp.fxml"));
        Parent addBPRoot = loader.load();
        AddToBPController addToBPController = loader.getController();
        addToBPController.set(user);

        Stage stage = new Stage();
        stage.setTitle("Add to Backpack");
        stage.setScene(new Scene(addBPRoot));
        stage.show();

        Stage homeStage = (Stage) addToBpButton.getScene().getWindow();
        homeStage.close();
    }

    /**
     * Open share window after clicking share button.
     *
     * @throws IOException the io exception
     */
    private void openShareWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("share.fxml"));
        Parent addBPRoot = loader.load();
        ShareController shareController = loader.getController();
        shareController.set(user);

        Stage stage = new Stage();
        stage.setTitle("Sharing your trip");
        stage.setScene(new Scene(addBPRoot));
        stage.show();

        Stage homeStage = (Stage) shareButton.getScene().getWindow();
        homeStage.close();
    }

    /**
     * Open car animation window after adding trip to the user.
     *
     * @throws IOException the io exception
     */
    private void openCarAnimationWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("car.fxml"));
        Parent root = loader.load();

        Stage animationStage = new Stage();

        animationStage.setTitle("Car Animation");
        animationStage.setScene(new Scene(root));
        animationStage.showAndWait();
    }

    /**
     * open create trip window after clicking create trip button.
     *
     * @throws IOException the io exception
     */
    private void openCreateTripWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create_trip.fxml"));
        Parent addBPRoot = loader.load();
        CreateTripController createTripController = loader.getController();
        createTripController.set(user);

        Stage stage = new Stage();
        stage.setTitle("Create new trip");
        stage.setScene(new Scene(addBPRoot));
        stage.show();

        Stage homeStage = (Stage) createTripButton.getScene().getWindow();
        homeStage.close();
    }
}