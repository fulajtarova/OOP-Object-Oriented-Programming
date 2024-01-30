package com.example.triparchitect.trips;

import com.example.triparchitect.ResultHolder;
import com.example.triparchitect.users.User;

/**
 * Trip factory class creates a trip for the user based on their user type and budget
 */
public class TripFactory {
    /**
     * Create trip based on user type and budget
     *
     * @param user the user
     * @return result holder object that contains the trip object or an error message
     */
    public ResultHolder<?> createTrip(User user) {
        String userType = user.getUserType().getUserTypeDescription();

        Trip userTrip = switch (userType) {
            case "Adventure seeker" ->
                    user.getBudget() > 1000 ? new LuxuryAdventureSeekerTrip(user) : new AdventureSeekerTrip(user);
            case "Culture explorer" ->
                    user.getBudget() > 1000 ? new LuxuryCultureExplorerTrip(user) : new CultureExplorerTrip(user);
            case "Nature lover" ->
                    user.getBudget() > 1000 ? new LuxuryNatureLoverTrip(user) : new NatureLoverTrip(user);
            case "Foodie" -> user.getBudget() > 1000 ? new LuxuryFoodieTrip(user) : new FoodieTrip(user);
            default -> null;
        };

        if (userTrip != null) {
            return new ResultHolder<>(userTrip);
        } else {
            return new ResultHolder<>("Unable to create a trip for this user type.");
        }
    }
}
