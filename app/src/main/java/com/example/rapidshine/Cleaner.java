package com.example.rapidshine;

public class Cleaner {
    private String name;
    private double hourlyRate;
    private float rating;
    private int imageResource;
    private String imageName;
    private int age;
    private int reviews;
    private int experienceYears;
    private int cleansCompleted;
    private String about;

    public Cleaner(String name, double hourlyRate, float rating, int imageResource) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.rating = rating;
        this.imageResource = imageResource;
    }

    public Cleaner() {
        // Required empty constructor for Firestore
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public int getCleansCompleted() {
        return cleansCompleted;
    }

    public void setCleansCompleted(int cleansCompleted) {
        this.cleansCompleted = cleansCompleted;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
