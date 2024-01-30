package com.example.triparchitect.weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * WeatherActivityFactory class that generates activities based on the weather
 */
public class WeatherActivityFactory {
    /**
     * Generate trips by weather list.
     *
     * @param weather the weather
     * @return the list of activities for certain weather
     */
    public List<String> generateTripsByWeather(Weather weather) {
        Map<Weather, List<String>> weatherTrips = new HashMap<>();

        weatherTrips.put(Weather.SUNNY, List.of("Ride a bike", "Have a picnic"));
        weatherTrips.put(Weather.RAINY, List.of("Movie marathon", "Puzzles"));
        weatherTrips.put(Weather.CLOUDY, List.of("Read a book", "DIY or craft projects"));
        weatherTrips.put(Weather.SNOWY, List.of("Build a snowman", "Snowball fight"));

        return weatherTrips.getOrDefault(weather, new ArrayList<>());
    }
}