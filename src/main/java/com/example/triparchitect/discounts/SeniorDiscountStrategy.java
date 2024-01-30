package com.example.triparchitect.discounts;

/**
 * class for creating senior discounts and implementing DiscountStrategy interface using inheritance and strategy pattern.
 */
public class SeniorDiscountStrategy implements DiscountStrategy {
    /**
     * Apply discount for the trip based on the user type
     *
     * @param originalPrice original price of the trip
     * @return discounted price of the trip
     */
    public double applyDiscount(double originalPrice) {
        return originalPrice * 0.7;
    }
}
