package com.example.triparchitect;

/**
 * ResultHolder class demonstrating generics
 * used to hold the result of a method, in this case the result of selected trip
 *
 * @param <T> the type parameter
 */
public record ResultHolder<T>(T result) {
    /**
     * Instantiates a new Result holder.
     *
     * @param result the result
     */
    public ResultHolder {
    }

    /**
     * Gets result.
     *
     * @return result
     */
    public T result() {
        return result;
    }
}
