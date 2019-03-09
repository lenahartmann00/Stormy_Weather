package com.lenahartmann00.stormyweather.model;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.lenahartmann00.stormyweather.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocation {


    private double latitude;
    private  double longitude;
    private String city;


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
