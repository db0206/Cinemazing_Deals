package com.example.Cinemazing_Deals.model;

public enum Category {
    NEW_GEAR("new_gear", 1),
    RENTAL_GEAR("rental_gear", 2),
    PRODUCTION("production", 3),
    POST_PRODUCTION("post_production", 4),
    MOVIE_THEATERS("movie_theaters", 5),
    STREAMING("streaming", 6),
    DVD_AND_BLUERAY("dvd_and_bluerays", 7);

    private String name;
    private int serialNumber;

    Category(String name, int serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
