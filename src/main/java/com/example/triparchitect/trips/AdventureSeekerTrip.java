package com.example.triparchitect.trips;

import com.example.triparchitect.users.User;

/**
 * Adventure seeker trip class creates a trip for the adventure seeker user
 */
public class AdventureSeekerTrip extends Trip {
    /**
     * constructor for the adventure seeker trip
     *
     * @param user user object
     */
    public AdventureSeekerTrip(User user) {
        super(user);
    }

    /**
     * add unique options for adventure seeker trip
     */
    @Override
    public void addUniqueOptions() {
        places.add("Bungee jumping");
        price.add(120);
        places.add("Water park");
        price.add(20);
    }

    /**
     * add luxury options for adventure seeker trip
     */
    @Override
    public void addLuxuryOptions() {
    }
}
