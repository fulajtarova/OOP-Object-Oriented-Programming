package com.example.triparchitect.discounts;

import java.io.Serializable;

/**
 * The interface Discount strategy for creating different discount strategies with strategy pattern.
 */
public interface DiscountStrategy extends Serializable {
    /**
     * Apply discount for the trip based on the user type
     *
     * @param originalPrice original price of the trip
     * @return discounted price of the trip
     */
    double applyDiscount(double originalPrice);
}

