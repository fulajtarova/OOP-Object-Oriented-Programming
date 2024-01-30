package com.example.triparchitect.trips;

import com.example.triparchitect.users.User;

/**
 * Nature lover trip class creates a trip for the nature lover user
 */
public class NatureLoverTrip extends Trip {
    /**
     * Constructor for nature lover trip
     *
     * @param user the user
     */
    public NatureLoverTrip(User user) {
        super(user);
    }

    /**
     * Add unique options for nature lover trip
     */
    @Override
    public void addUniqueOptions() {
        places.add("Camping");
        price.add(30);
        places.add("Paddle-boarding");
        price.add(15);
    }

    /**
     * Add luxury options for nature lover trip
     */
    @Override
    public void addLuxuryOptions() {
    }
}
