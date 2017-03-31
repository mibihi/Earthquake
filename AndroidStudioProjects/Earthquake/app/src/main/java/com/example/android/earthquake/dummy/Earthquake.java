package com.example.android.earthquake.dummy;

/**
 * Created by mibihi on 3/20/17.
 */

public class Earthquake {
    double magnitude;
    String location ;
    long TimeInMilliseconds;

    public double getMagnitude() {
        return magnitude;
    }

    public Earthquake(double magnitude, String location, long TimeInMilliseconds, String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.TimeInMilliseconds = TimeInMilliseconds;
        this.url = url;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTimeInMilliseconds() {
        return TimeInMilliseconds;
    }

    public void setTimeInMilliseconds(long timeInMilliseconds) {
        this.TimeInMilliseconds = timeInMilliseconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;
}
