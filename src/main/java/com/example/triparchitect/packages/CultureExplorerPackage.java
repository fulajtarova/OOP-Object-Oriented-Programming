package com.example.triparchitect.packages;

import com.example.triparchitect.users.User;

/**
 * Culture explorer package inherits from Package
 * creating new unique essentials for users backpack based on their user type
 */
public class CultureExplorerPackage extends Package {
    /**
     * constructor for the class that calls the constructor of the parent class
     *
     * @param user user object
     */
    public CultureExplorerPackage(User user) {
        super(user);
    }

    /**
     * displays the package details what the user will get in their backpack based on their user type
     *
     * @return String package details
     */
    @Override
    public String getPackageDetails() {
        return "Culture explorer package includes city map and museum tickets.";
    }

    /**
     * adds unique essentials to the package based on the user type
     */
    @Override
    public void addUniqueEssentials() {
        essentials.add("City map");
        essentials.add("Museum tickets");
    }
}
