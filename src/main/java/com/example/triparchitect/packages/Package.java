package com.example.triparchitect.packages;

import com.example.triparchitect.users.Student;
import com.example.triparchitect.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Package class that creates a package for the every user
 */
public class Package {

    /**
     * User object
     */
    private final User user;
    /**
     * List of essentials for the user
     */
    protected List<String> essentials;

    /**
     * constructor for the class
     *
     * @param user user object
     */
    public Package(User user) {
        this.user = user;
        this.essentials = new ArrayList<>();

        essentials.add("Sandwich");
        essentials.add("Water");

        if (user instanceof Student) {
            essentials.add("Movie tickets (student benefit)");
        }
        addUniqueEssentials();
    }

    /**
     * displays the package details for the user based on their user type
     *
     * @return the package details
     */
    public String getPackageDetails() {
        return null;
    }

    /**
     * adds unique essentials to the package based on the user type
     */
    public void addUniqueEssentials() {
    }

    /**
     * prepares the backpack for the user
     */
    public void prepareBackpack() {
        for (String item : essentials) {
            user.addToBackpack(item);
        }
    }


}
