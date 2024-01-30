package com.example.triparchitect;

/**
 * CombinedTableEntry class to hold data for the table
 */
public class CombinedTableEntry {
    /**
     * Trips that user has created
     */
    private final String trip;
    /**
     * Backpacks that user has
     */
    private final String backpack;
    /**
     * Date of the trips that user has created
     */
    private final String date;
    /**
     * Trips that user's friends have created and shared with user
     */
    private final String friendsTrip;


    /**
     * Instantiates a new Combined table entry.
     *
     * @param trip        the trip that user has created
     * @param backpack    the backpack that user has
     * @param date        the date of the trip that user has created
     * @param friendsTrip the friends trip that user's friends have created and shared with user
     */
    public CombinedTableEntry(String trip, String backpack, String date, String friendsTrip) {
        this.trip = trip;
        this.backpack = backpack;
        this.date = date;
        this.friendsTrip = friendsTrip;

    }

    /**
     * Gets trip name.
     *
     * @return the trip
     */
    public String getTrip() {
        return trip;
    }

    /**
     * Gets backpack item.
     *
     * @return the backpack item
     */
    public String getBackpack() {
        return backpack;
    }

    /**
     * Gets date of the trip.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets shared trip
     *
     * @return the shared trip with user
     */
    public String getSharedTrip() {
        return friendsTrip;
    }
}

