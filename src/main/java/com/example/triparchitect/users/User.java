package com.example.triparchitect.users;

import com.example.triparchitect.discounts.DiscountStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Main user class that stores user information
 */
public class User implements Serializable {
    /**
     * User name
     */
    private final String username;
    /**
     * password
     */
    private final String password;
    /**
     * List of items in the backpack
     */
    private final List<String> backpack;
    /**
     * List of trips that the user has booked
     */
    private final List<String> myTrips;
    /**
     * List of dates that the user has booked
     */
    private final List<String> dates;
    /**
     * User type
     */
    private UserType userType;
    /**
     * User budget
     */
    private int budget;
    /**
     * User age
     */
    private int age;
    /**
     * if the user has initialized the backpack, so we would not initialize it again
     */
    private boolean backpackInitialized;
    /**
     * if the user has applied the bonus, so we would not apply it again
     */
    private boolean bonusApplied;
    /**
     * Discount strategy for the user, individual type of user has different discount strategy
     */
    private DiscountStrategy discountStrategy;
    /**
     * List of observers for the user
     */
    private List<UserObserver> observers = new ArrayList<>();
    /**
     * Bonus for the user, individual type of user has different bonus
     */
    private Bonus bonus;
    //-------------------------------------------------------------------------------------------------------

    /**
     * Instantiates a new User.
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.backpack = new ArrayList<>();
        this.myTrips = new ArrayList<>();
        this.dates = new ArrayList<>();
    }

    /**
     * Instantiates a new User with all the information
     * it is used for copying the user information to the new type of user - student, senior
     *
     * @param user the user
     */
    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userType = user.getUserType();
        this.budget = user.getBudget();
        this.age = user.getAge();
        this.backpackInitialized = user.isBackpackInitialized();
        this.backpack = user.getBackpack();
        this.bonusApplied = user.isBonusApplied();
        this.myTrips = user.getMyTrips();
        this.dates = user.getDates();
        this.observers = user.observers;
    }
    //-------------------------------------------------------------------------------------------------------

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets user type.
     *
     * @return the user type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Gets budget.
     *
     * @return the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Sets budget.
     *
     * @param budget the budget
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets if the backpack was initialized
     *
     * @return the boolean
     */
    public boolean isBackpackInitialized() {
        return backpackInitialized;
    }

    /**
     * Sets initialized backpack.
     *
     * @param backpackInitialized the backpack initializing
     */
    public void setBackpackInitialized(boolean backpackInitialized) {
        this.backpackInitialized = backpackInitialized;
    }

    /**
     * Gets backpack.
     *
     * @return the backpack
     */
    public List<String> getBackpack() {
        return backpack;
    }

    /**
     * Is bonus applied boolean.
     *
     * @return the boolean
     */
    public boolean isBonusApplied() {
        return bonusApplied;
    }

    /**
     * Sets bonus applied.
     *
     * @param bonusApplied the bonus applied
     */
    public void setBonusApplied(boolean bonusApplied) {
        this.bonusApplied = bonusApplied;
    }

    /**
     * Gets dates.
     *
     * @return the dates
     */
    public List<String> getDates() {
        return dates;
    }

    /**
     * Gets my trips.
     *
     * @return my trips
     */
    public List<String> getMyTrips() {
        return myTrips;
    }
    //-------------------------------------------------------------------------------------------------------

    /**
     * Updating the user information after filling the form in the registration
     *
     * @param budget   the budget of the user
     * @param age      the age of the user
     * @param userType the user type of the user
     */
    public void updateUser(int budget, int age, UserType userType) {
        this.budget = budget;
        this.age = age;
        this.userType = userType;
    }

    /**
     * Welcome message string.
     *
     * @return the string with the welcome message
     */
    public String WelcomeMessage() {
        return "Welcome!";
    }

    /**
     * Goodbye message string.
     *
     * @return the string with the goodbye message
     */
    public String GoodbyeMessage() {
        return "Goodbye!";
    }


    /**
     * Add item to backpack.
     *
     * @param item the item that the user wants to add to the backpack
     */
    public void addToBackpack(String item) {
        backpack.add(item);
    }

    /**
     * Remove item from backpack.
     *
     * @param item the item that the user wants to remove from the backpack
     */
    public void removeItemFromBackpack(String item) {
        if (backpack != null) {
            backpack.remove(item);
        }
    }

    /**
     * Add trip to user trips.
     *
     * @param item the item that the user wants to add to the trips
     */
    public void addToTrips(String item) {
        myTrips.add(item);
    }

    /**
     * Add date of the trip to the dates.
     *
     * @param item the date of the trip
     */
    public void addToDates(String item) {
        dates.add(item);
    }

    /**
     * Deduct trip cost from the users budget.
     *
     * @param tripCost the trip cost
     */
    public void deductTripCost(int tripCost) {
        this.budget -= tripCost;
    }
    //-------------------------------------------------------------------------------------------------------

    /**
     * Apply bonus to the user.
     */
    public void applyBonus() {
        if (bonus != null && !isBonusApplied()) {
            setBudget(getBudget() + bonus.getBonusAmount());
            System.out.println(bonus.getBonusMessage());
            setBonusApplied(true);
        }
    }

    /**
     * Sets bonus to the user, if the user is a student or a senior they receive a money bonus
     *
     * @param bonus the bonus
     */
    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    /**
     * Apply discount for trips based on the user type
     *
     * @param originalPrice the original price
     * @return discounted price
     */
    public double applyDiscount(double originalPrice) {
        if (discountStrategy != null) {
            return discountStrategy.applyDiscount(originalPrice);
        }
        return originalPrice;
    }
    //-------------------------------------------------------------------------------------------------------

    /**
     * Add observer that will be notified when the user changes
     *
     * @param observer the observer
     */
    public void addObserver(UserObserver observer) {
        observers.add(observer);
    }

    /**
     * Method that will be called when the user changes
     */
    public void userChanged() {
        notifyObservers();
    }

    /**
     * Notify observers.
     */
    private void notifyObservers() {
        for (UserObserver observer : observers) {
            observer.onUserUpdated(this);
        }
    }

    /**
     * The interface User observer.
     */
    public interface UserObserver extends Serializable {
        /**
         * On user updated.
         *
         * @param user the user
         */
        void onUserUpdated(User user);
    }

    /**
     * Different notifications when the user changes - new user created
     */
    public static class UserNotification implements UserObserver {
        /**
         * @param user the user that was created
         */
        public void onUserUpdated(User user) {
            if (user instanceof Student) {
                System.out.println("New student user created: " + user.getUsername());
            } else if (user instanceof Senior) {
                System.out.println("New senior user created: " + user.getUsername());
            }
        }
    }
    //-------------------------------------------------------------------------------------------------------

    /**
     * The interface of the bonus.
     */
    public interface Bonus extends Serializable {
        /**
         * Gets bonus amount.
         *
         * @return the bonus amount
         */
        int getBonusAmount();

        /**
         * Gets bonus message.
         *
         * @return the bonus message
         */
        String getBonusMessage();
    }

    /**
     * Student bonus class that holds the bonus amount and the bonus message information
     */
    public static class StudentBonus implements Bonus {
        /**
         * @return the bonus amount that the student will receive
         */
        public int getBonusAmount() {
            return 50;
        }

        /**
         * @return the bonus message that will be displayed
         */
        public String getBonusMessage() {
            return "Congratulations, you received 50 € on your account as a student bonus!";
        }
    }

    /**
     * Senior bonus class that holds the bonus amount and the bonus message information
     */
    public static class SeniorBonus implements Bonus {
        /**
         * @return the bonus amount that the senior will receive
         */
        public int getBonusAmount() {
            return 30;
        }

        /**
         * @return the bonus message that will be displayed
         */
        public String getBonusMessage() {
            return "Congratulations, you received 80 € on your account as senior bonus!";
        }
    }
    //-------------------------------------------------------------------------------------------------------

    /**
     * The interface User type that will be implemented by the concrete user types
     */
    public static class UserType implements Serializable {
        /**
         * Gets user type description.
         *
         * @return the user type description
         */
        public String getUserTypeDescription() {
            return null;
        }

        /**
         * Print user type
         */
        public void printUserType() {
        }
    }

    /**
     * Adventure seeker user type class
     */
    public static class AdventureSeeker extends UserType {
        /**
         * @return the user type description
         */
        @Override
        public String getUserTypeDescription() {
            return "Adventure seeker";
        }

        /**
         * Print user type
         */
        @Override
        public void printUserType() {
            System.out.println("User type: Adventure seeker");
        }
    }


    /**
     * Culture explorer user type class
     */
    public static class CultureExplorer extends UserType {
        /**
         * @return the user type description
         */
        @Override
        public String getUserTypeDescription() {
            return "Culture explorer";
        }

        /**
         * print user type
         */
        @Override
        public void printUserType() {
            System.out.println("User type: Culture explorer");
        }
    }

    /**
     * Nature lover user type class
     */
    public static class NatureLover extends UserType {
        /**
         * @return the user type description
         */
        @Override
        public String getUserTypeDescription() {
            return "Nature lover";
        }

        /**
         * print user type
         */
        @Override
        public void printUserType() {
            System.out.println("User type: Nature lover");
        }
    }

    /**
     * Foodie user type class
     */
    public static class Foodie extends UserType {
        /**
         * @return the user type description
         */
        @Override
        public String getUserTypeDescription() {
            return "Foodie";
        }

        /**
         * print user type
         */
        @Override
        public void printUserType() {
            System.out.println("User type: Foodie");
        }
    }
}