package com.example.triparchitect.decorator;

/**
 * Class for creating personalized items with decorator pattern and extending ItemDecorator class using inheritance and decorator pattern.
 */
public class PersonalizedItem extends ItemDecorator {
    /**
     * Instantiates a new Personalized item.
     *
     * @param item item object to be decorated
     */
    public PersonalizedItem(Item item) {
        super(item);
    }

    /**
     * getter method for description
     *
     * @return description of the item
     */
    public String getDescription() {
        return item.getDescription() + " (personalized)";
    }

    /**
     * getter method for price
     *
     * @return price of the item
     */
    public int getPrice() {
        return item.getPrice();
    }
}

