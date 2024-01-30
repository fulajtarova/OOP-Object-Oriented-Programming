package com.example.triparchitect.trips;

import com.example.triparchitect.users.Student;
import com.example.triparchitect.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Trip class creates a trip for the user
 */
public abstract class Trip implements TripExtras {
    /**
     * User
     */
    protected static User user;
    /**
     * Price of the places that the user can visit
     */
    protected static List<Integer> price;
    /**
     * Places that the user can visit
     */
    protected List<String> places;

    /**
     * Constructor for the trip
     *
     * @param user user object
     */
    public Trip(User user) {
        Trip.user = user;
        this.places = new ArrayList<>();
        price = new ArrayList<>();

        places.add("Spa");
        price.add(30);
        places.add("Music concert");
        price.add(50);

        if (user instanceof Student) {
            places.add("Cinema");
            price.add(0);
        }


        addUniqueOptions();
        addLuxuryOptions();
        applyDiscount();
        addExtraOptions(places, price);
    }

    /**
     * Apply discount to the price of the places
     */
    public void applyDiscount() {
        price.replaceAll(originalPrice -> (int) user.applyDiscount(originalPrice));
    }

    /**
     * Getter method
     *
     * @return the places that the user can visit
     */
    public List<String> getPlaces() {
        return places;
    }

    /**
     * Getter method for the price of the places
     *
     * @return the price of the places
     */
    public List<Integer> getPrice() {
        return price;
    }

    /**
     * Generate only the affordable places for the user
     *
     * @return the affordable places
     */
    public List<String> getAffordablePlaces() {
        int userBudget = user.getBudget();
        return places.stream().filter(place -> {
            int index = places.indexOf(place);
            return price.get(index) <= userBudget;
        }).collect(Collectors.toList());
    }

    /**
     * Add unique options for the trip based on the user type
     */
    public void addUniqueOptions() {

    }

    /**
     * Add luxury options for the trip based on the user budget
     */

    public void addLuxuryOptions() {

    }

    /**
     * Add extra options for the trip overriding the default method and using implicit implementation
     *
     * @param places places to visit in the trip
     * @param prices prices of the places
     */
    @Override
    public void addExtraOptions(List<String> places, List<Integer> prices) {
        places.add("City tour");
        prices.add(10);

        places.add("Boat trip");
        prices.add(22);

        places.add("Beach trip");
        prices.add(5);

        places.add("Ski trip");
        prices.add(60);
    }
}
