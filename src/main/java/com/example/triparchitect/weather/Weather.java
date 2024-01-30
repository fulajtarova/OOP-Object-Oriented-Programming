package com.example.triparchitect.weather;

import java.util.Random;

/**
 * The enum Weather for generating random weather.
 */
public enum Weather {
    /**
     * Sunny weather.
     */
    SUNNY,
    /**
     * Rainy weather.
     */
    RAINY,
    /**
     * Cloudy weather.
     */
    CLOUDY,
    /**
     * Snowy weather.
     */
    SNOWY;

    /**
     * Gets random weather.
     *
     * @return weather the random weather
     */
    public static Weather getRandomWeather() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
