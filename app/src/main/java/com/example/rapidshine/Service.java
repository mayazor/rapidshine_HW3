package com.example.rapidshine;

public class Service {
    private String name;
    private int imageResource;
    private String description;
    private double price;
    private String imageName; // For Firestore compatibility

    public Service(String name, int imageResource, String description, double price) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.price = price;
    }

    // Required empty constructor for Firestore
    public Service() {
        // Firestore requires an empty constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}

