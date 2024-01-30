package com.example.triparchitect.trips;

import com.example.triparchitect.users.User;

/**
 * luxury adventure seeker trip for users with budget of 1000 or more
 */
public class LuxuryAdventureSeekerTrip extends AdventureSeekerTrip {

    /**
     * constructor for luxury adventure seeker trip
     *
     * @param user the user
     */
    public LuxuryAdventureSeekerTrip(User user) {
        super(user);
    }

    /**
     * add luxury options for luxury adventure seeker trip
     */
    @Override
    public void addLuxuryOptions() {
        places.add("Visit Machu Picchu");
        price.add(200);
        places.add("Visit the Grand Canyon");
        price.add(300);
    }
}
