package com.example.triparchitect.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Share class to store share information for users
 * Each share contains the user who shared the trip, the user who received the trip, the trip name and the trip date
 */
public class Share implements Serializable {
    /**
     * list of users who receive the trip
     */
    private final List<String> to;
    /**
     * list of users who share the trip
     */
    private final List<String> from;
    /**
     * list of shared trip names
     */
    private final List<String> trip;
    /**
     * list of shared trip dates
     */
    private final List<String> date;

    /**
     * Instantiates a new sharing object.
     */
    public Share() {
        this.to = new ArrayList<>();
        this.from = new ArrayList<>();
        this.trip = new ArrayList<>();
        this.date = new ArrayList<>();
    }

    /**
     * Setting shared trip information for further use
     *
     * @param toUser   set the user who receives the trip
     * @param fromUser set the user who shares the trip
     * @param tripName the trip name
     * @param tripDate the trip date
     */
    public void addShare(String toUser, String fromUser, String tripName, String tripDate) {
        this.to.add(toUser);
        this.from.add(fromUser);
        this.trip.add(tripName);
        this.date.add(tripDate);
    }

    /**
     * Getter for the user who receives the trip
     *
     * @return user who receives the trip
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * Getter for the user who shares the trip
     *
     * @return user who shares the trip
     */
    public List<String> getFrom() {
        return from;
    }

    /**
     * Getter for the trip name
     *
     * @return trip name
     */
    public List<String> getTrip() {
        return trip;
    }

    /**
     * Getter for the trip date
     *
     * @return trip date
     */
    public List<String> getDate() {
        return date;
    }
}

