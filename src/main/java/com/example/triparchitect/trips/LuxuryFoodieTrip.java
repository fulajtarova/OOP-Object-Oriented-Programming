package com.example.triparchitect.trips;

import com.example.triparchitect.users.User;

/**
 * Luxury foodie trip for users with budget of 1000 or more
 */
public class LuxuryFoodieTrip extends FoodieTrip {
    /**
     * Constructor for luxury foodie trip
     *
     * @param user the user
     */
    public LuxuryFoodieTrip(User user) {
        super(user);
    }

    /**
     * Add luxury options for luxury foodie trip
     */
    @Override
    public void addLuxuryOptions() {
        places.add("Visit the Colosseum");
        price.add(200);
        places.add("Visit the Sagrada Familia");
        price.add(300);
    }
}
