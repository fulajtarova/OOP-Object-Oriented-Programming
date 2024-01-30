package com.example.triparchitect.packages;

import com.example.triparchitect.users.User;

/**
 * NatureLover package inherits from Package class
 * creating new unique essentials for users backpack based on their user type
 */
public class NatureLoverPackage extends Package {
    /**
     * constructor for the class
     *
     * @param user user object
     */
    public NatureLoverPackage(User user) {
        super(user);
    }

    /**
     * displays the package details for the user based on their user type
     *
     * @return package details and displays message
     */
    @Override
    public String getPackageDetails() {
        return "Nature lover package includes binoculars and camping gear.";
    }

    /**
     * adds unique essentials to the package based on the user type
     */
    @Override
    public void addUniqueEssentials() {
        essentials.add("Binoculars");
        essentials.add("Camping gear");
    }
}
