package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class SoilImage {

    Bitmap soil;
    double latitude;
    double longtitude;
    int plant_id;

    public SoilImage(Bitmap soil, double latitude, double longtitude, int plant_id) {
        this.soil = soil;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.plant_id = plant_id;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public Bitmap getSoil() {
        return soil;
    }

    public void setSoil(Bitmap soil) {
        this.soil = soil;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
