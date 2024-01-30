package com.example.triparchitect.trips;

import com.example.triparchitect.users.User;

/**
 * Culture explorer trip class creates a trip for the culture explorer user
 */
public class CultureExplorerTrip extends Trip {
    /**
     * constructor for culture explorer trip.
     *
     * @param user user
     */
    public CultureExplorerTrip(User user) {
        super(user);
    }

    /**
     * add unique options for culture explorer trip.
     */
    @Override
    public void addUniqueOptions() {
        places.add("Art gallery");
        price.add(10);
        places.add("Visit church");
        price.add(5);
    }

    /**
     * add luxury options for culture explorer trip.
     */
    @Override
    public void addLuxuryOptions() {
    }
}
