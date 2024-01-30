package com.example.triparchitect.trips;

import com.example.triparchitect.users.User;

/**
 * luxury culture explorer trip for users with budget of 1000 or more
 */
public class LuxuryCultureExplorerTrip extends CultureExplorerTrip {
    /**
     * constructor for luxury culture explorer trip
     *
     * @param user the user
     */
    public LuxuryCultureExplorerTrip(User user) {
        super(user);
    }

    /**
     * add luxury options for luxury culture explorer trip
     */
    @Override
    public void addLuxuryOptions() {
        places.add("Visit the Eiffel Tower");
        price.add(200);
        places.add("Visit the Pyramids of Giza");
        price.add(300);
    }
}
