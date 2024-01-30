package com.example.triparchitect.packages;

import com.example.triparchitect.users.User;

import java.util.Optional;


/**
 * PackageFactory class is responsible for creating Package objects based on user type that will be added to the user's backpack
 */
public class PackageFactory {

    /**
     * this method creates a package based on the user type
     *
     * @param user user whose package is to be created
     * @return new object of type Package
     */
    public Optional<Package> createPackage(User user) {
        String userType = user.getUserType().getUserTypeDescription();
        Package userPackage = switch (userType) {
            case "Adventure seeker" -> new AdventureSeekerPackage(user);
            case "Culture explorer" -> new CultureExplorerPackage(user);
            case "Nature lover" -> new NatureLoverPackage(user);
            case "Foodie" -> new FoodiePackage(user);
            default -> null;
        };

        return Optional.ofNullable(userPackage);
    }
}
