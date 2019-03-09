package com.lenahartmann00.stormyweather.model;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.lenahartmann00.stormyweather.R;
import com.lenahartmann00.stormyweather.ui.MainActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocation {


    private double latitude;
    private  double longitude;
    private String city;


    private void getCurrentLocationData() {
        if (ActivityCompat.checkSelfPermission(, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else{
            updateCurrentLocation();
        }
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
