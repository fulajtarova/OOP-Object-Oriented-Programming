package com.example.triparchitect.users;

import com.example.triparchitect.discounts.DiscountStrategy;
import com.example.triparchitect.discounts.StudentDiscountStrategy;

/**
 * Student class creates a student user
 */
public class Student extends User {

    /**
     * Discount strategy for student user because they get 20% off on all trips
     */
    private final DiscountStrategy discountStrategy;

    /**
     * Instantiates a new Student.
     *
     * @param user user object
     */
    public Student(User user) {
        super(user);
        setBonus(new StudentBonus());
        applyBonus();
        discountStrategy = new StudentDiscountStrategy();
        userChanged();
    }


    /**
     * @param price original price of the trip
     * @return double value of the discounted price
     */
    @Override
    public double applyDiscount(double price) {
        return discountStrategy.applyDiscount(price);
    }

    /**
     * @return String value of the welcome message for the student user
     */
    @Override
    public String WelcomeMessage() {
        return "Welcome Student!";
    }

    /**
     * @return String value of the goodbye message for the student user
     */
    @Override
    public String GoodbyeMessage() {
        return "Goodbye Student!";
    }
}
