package com.example.triparchitect.decorator;

/**
 * Abstract ItemDecorator class for creating items with decorator pattern and implementing inheritance.
 */
public abstract class ItemDecorator {
    /**
     * item object to be decorated
     */
    protected Item item;

    /**
     * Instantiates a new Item decorator.
     *
     * @param item item object to be decorated
     */
    public ItemDecorator(Item item) {
        this.item = item;
    }

    /**
     * getter method for description
     *
     * @return description of the item
     */
    public abstract String getDescription();

    /**
     * getter method for price
     *
     * @return price of the item
     */
    public abstract int getPrice();
}

