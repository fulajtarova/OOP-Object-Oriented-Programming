package com.example.triparchitect.exceptions;

/**
 * custom exception class for situations when a user cannot be classified as both Student and Senior
 */
public class YouCantBeStudentAndSenior extends Exception {
    /**
     * constructor for the exception
     *
     * @param message message that is to be displayed when the exception is thrown
     */
    public YouCantBeStudentAndSenior(String message) {
        super(message);
    }
}
