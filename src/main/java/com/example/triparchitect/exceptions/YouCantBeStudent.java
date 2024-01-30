package com.example.triparchitect.exceptions;

/**
 * custom exception class for situations when a user cannot be classified as a Student
 */
public class YouCantBeStudent extends Exception {
    /**
     * constructor for the exception
     *
     * @param message message that is to be displayed when the exception is thrown
     */
    public YouCantBeStudent(String message) {
        super(message);
    }
}
