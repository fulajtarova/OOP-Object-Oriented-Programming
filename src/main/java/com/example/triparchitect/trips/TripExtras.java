package com.example.triparchitect.trips;

import java.util.List;

/**
 * Add extra options to trips that implement this interface
 */
public interface TripExtras {
    /**
     * Add extra options.
     *
     * @param places places to visit
     * @param prices prices of the places
     */
    default void addExtraOptions(List<String> places, List<Integer> prices) {
        places.add("LSD trip");
        prices.add(30);

    }
}

