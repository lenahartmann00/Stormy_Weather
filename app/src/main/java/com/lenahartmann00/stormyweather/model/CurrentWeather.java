package com.lenahartmann00.stormyweather.model;

import com.lenahartmann00.stormyweather.R;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class CurrentWeather {
    private String locationLabel;
    private String icon;
    private long time;
    private double temperature;
    private double humidity;
    private double precipChance;
    private String summary;
    private String timezone;

    public CurrentWeather() {

    }

    public CurrentWeather(String locationLabel, String icon, long time, double temperature, double humidity, double precipChance, String summary, String timezone) {
        this.locationLabel = locationLabel;
        this.icon = icon;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.precipChance = precipChance;
        this.summary = summary;
        this.timezone = timezone;
    }


    //Getter and Setter
    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public String getIcon() {
        return icon;
    }

    public int getIconId() {
        //clear-day, clear-night, rain, snow, sleet, wind, fog,
        // cloudy, partly-cloudy-day, or partly-cloudy-night
        int iconId;
        switch (getIcon()) {
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "cloudy":
                iconId = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
            default:
                iconId = R.drawable.clear_day;
        }
        return iconId;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTime() {
        return time;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.US); //h -> hours without leading zero,
        // mm -> minutes with leading zero,
        // a -> AM/PM
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        return formatter.format(time * 1000); //time has to be in s
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPrecipChance() {
        return precipChance;
    }

    public void setPrecipChance(double precipChance) {
        this.precipChance = precipChance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }


}
