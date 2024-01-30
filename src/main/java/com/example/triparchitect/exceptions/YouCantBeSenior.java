package com.example.triparchitect.exceptions;

import java.io.IOException;

/**
 * custom exception class for situations when a user cannot be classified as a Senior
 */
public class YouCantBeSenior extends IOException {
    /**
     * constructor for the exception
     *
     * @param message that is to be displayed when the exception is thrown
     */
    public YouCantBeSenior(String message) {
        super(message);
    }
}
