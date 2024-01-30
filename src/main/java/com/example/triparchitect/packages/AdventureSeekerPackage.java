package com.example.triparchitect.packages;

import com.example.triparchitect.users.User;

/**
 * Adventure seeker package inherits from Package class
 * creating new unique essentials for users backpack based on their user type
 */
public class AdventureSeekerPackage extends Package {
    /**
     * constructor for the class
     *
     * @param user user object
     */
    public AdventureSeekerPackage(User user) {
        super(user);
    }

    /**
     * displays the package details for the user based on their user type
     *
     * @return String package details
     */
    @Override
    public String getPackageDetails() {
        return "Adventure seeker package includes hiking shoes and a sunglasses.";
    }

    /**
     * adds unique essentials to the package based on the user type
     */
    @Override
    public void addUniqueEssentials() {
        essentials.add("Hiking shoes");
        essentials.add("Sunglasses");
    }
}
