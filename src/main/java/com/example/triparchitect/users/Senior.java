package com.example.triparchitect.users;

import com.example.triparchitect.discounts.DiscountStrategy;
import com.example.triparchitect.discounts.SeniorDiscountStrategy;


/**
 * The type Senior.
 */
public class Senior extends User {

    /**
     * The Discount strategy because seniors get a 30% discount
     */
    private final DiscountStrategy discountStrategy;

    /**
     * Instantiates a new Senior.
     *
     * @param user the user
     */
    public Senior(User user) {
        super(user);
        setBonus(new SeniorBonus());
        applyBonus();
        discountStrategy = new SeniorDiscountStrategy();
        userChanged();
    }

    /**
     * @param price the original price of the trip
     * @return double value of the discounted price
     */
    @Override
    public double applyDiscount(double price) {
        return discountStrategy.applyDiscount(price);
    }

    /**
     * @return String value of the welcome message for the senior user
     */
    @Override
    public String WelcomeMessage() {
        return "Welcome Senior!";
    }

    /**
     * @return String value of the goodbye message for the senior user
     */
    @Override
    public String GoodbyeMessage() {
        return "Goodbye Senior!";
    }
}
