package com.example.rapidshine;

public class Service {
    private String name;
    private int imageResource;
    private String description;
    private double price;

    public Service(String name, int imageResource, String description, double price) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}

