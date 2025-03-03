package com.example.rapidshine;

public class Cleaner {
    private String name;
    private double hourlyRate;
    private float rating;
    private int imageResource;

    public Cleaner(String name, double hourlyRate, float rating, int imageResource) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.rating = rating;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public float getRating() {
        return rating;
    }

    public int getImageResource() {
        return imageResource;
    }
}
