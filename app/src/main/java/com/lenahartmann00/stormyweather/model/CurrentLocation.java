package com.lenahartmann00.stormyweather.model;

public class CurrentLocation {

    private double latitude;
    private double longitude;
    private String city;


    public CurrentLocation() {
        this.latitude = 37.8267;
        this.longitude = -122.4233;
        this.city = "Alcatraz Island, CA";
    }

    public CurrentLocation(double latitude, double longitude, String city) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    //Getter and Setter
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
