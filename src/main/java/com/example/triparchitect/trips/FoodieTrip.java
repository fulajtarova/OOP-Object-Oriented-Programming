package com.example.triparchitect.trips;

import com.example.triparchitect.users.User;

/**
 * Foodie trip class creates a trip for the foodie user
 */
public class FoodieTrip extends Trip {
    /**
     * constructor for foodie trip.
     *
     * @param user the user
     */
    public FoodieTrip(User user) {
        super(user);
    }

    /**
     * add unique options for foodie trip.
     */
    @Override
    public void addUniqueOptions() {
        places.add("Wine testing");
        price.add(15);
        places.add("Chocolate factory tour");
        price.add(20);
    }

    /**
     * add luxury options for foodie trip.
     */
    @Override
    public void addLuxuryOptions() {
    }
}
