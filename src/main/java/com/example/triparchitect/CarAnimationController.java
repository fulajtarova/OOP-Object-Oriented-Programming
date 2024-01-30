package com.example.triparchitect;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the car animation.
 */
public class CarAnimationController implements Initializable {

    /**
     * ImageView for the car image.
     */
    @FXML
    private ImageView carImageView;

    /**
     * Initializes the car animation and closes the window after the animation is finished.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        InputStream is = getClass().getResourceAsStream("/com/example/triparchitect/car.png");
        Image carImage = new Image(is);
        carImageView.setImage(carImage);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(4), carImageView);
        transition.setByX(580);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.setOnFinished(event -> carImageView.getScene().getWindow().hide());
        transition.play();
    }
}
