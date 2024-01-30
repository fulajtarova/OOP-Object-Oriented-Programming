package com.example.triparchitect.decorator;

/**
 * Base item class for creating items with decorator pattern.
 */
public class Item {
    private final String description;
    private final int price;

    /**
     * Instantiates a new Item.
     *
     * @param description description of the item
     * @param price       price of the item
     */
    public Item(String description, int price) {
        this.description = description;
        this.price = price;
    }

    /**
     * getter method for description
     *
     * @return description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter method for price
     *
     * @return price of the item
     */
    public int getPrice() {
        return price;
    }
}
