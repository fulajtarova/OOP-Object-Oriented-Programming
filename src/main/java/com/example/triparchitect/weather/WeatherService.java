package com.example.triparchitect.weather;

/**
 * WeatherService class that handles the weather details through multithreading
 */
public class WeatherService {
    /**
     * Gets weather.
     *
     * @return the weather
     */
    public Weather getWeather() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Weather.getRandomWeather();
    }
}