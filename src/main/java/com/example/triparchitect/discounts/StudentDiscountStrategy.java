package com.example.triparchitect.discounts;

/**
 * class for creating student discounts and implementing DiscountStrategy interface using inheritance and strategy pattern.
 */
public class StudentDiscountStrategy implements DiscountStrategy {
    /**
     * @param originalPrice the original price
     * @return discounted price of the trip
     */
    public double applyDiscount(double originalPrice) {
        return originalPrice * 0.8;
    }
}
