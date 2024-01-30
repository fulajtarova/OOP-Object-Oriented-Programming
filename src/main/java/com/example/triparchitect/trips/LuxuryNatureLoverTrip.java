package com.example.triparchitect.trips;

import com.example.triparchitect.users.User;

/**
 * Luxury nature lover trip for users with budget of 1000 or more
 */
// Inheritance: LuxuryNatureLoverTrip extends NatureLoverTrip
public class LuxuryNatureLoverTrip extends NatureLoverTrip {
    /**
     * Constructor for luxury nature lover trip
     *
     * @param user the user
     */
    public LuxuryNatureLoverTrip(User user) {
        super(user);
    }

    /**
     * Add luxury options for luxury nature lover trip
     */
    @Override
    public void addLuxuryOptions() {
        places.add("Visit the Great Barrier Reef");
        price.add(200);
        places.add("Visit the Stonehenge ");
        price.add(300);
    }
}
