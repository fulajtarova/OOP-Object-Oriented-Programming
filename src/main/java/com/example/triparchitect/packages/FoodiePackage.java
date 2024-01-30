package com.example.triparchitect.packages;

import com.example.triparchitect.users.User;

/**
 * Foodie package inherits from Package class
 * creating new unique essentials for users backpack based on their user type
 */
// Inheritance:
public class FoodiePackage extends Package {
    /**
     * constructor for the class
     *
     * @param user user object
     */
    public FoodiePackage(User user) {
        super(user);
    }

    /**
     * displays the package details for the user based on their user type
     *
     * @return String package details
     */
    @Override
    public String getPackageDetails() {
        return "Foodie package includes local cuisine guide and food tour tickets.";
    }

    /**
     * adds unique essentials to the package based on the user type
     */
    @Override
    public void addUniqueEssentials() {
        essentials.add("Local cuisine guide");
        essentials.add("Protein bar");
    }
}
