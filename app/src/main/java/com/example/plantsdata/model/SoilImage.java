package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class SoilImage {

    Bitmap soil;
    String plantHealth;
    String fruit;
    String flower;
    String leaves;
    double latitude;
    double longtitude;
    int plant_id;

    public SoilImage(Bitmap soil, String plantHealth, String fruit, String flower, String leaves, double latitude, double longtitude, int plant_id) {
        this.soil = soil;
        this.plantHealth = plantHealth;
        this.fruit = fruit;
        this.flower = flower;
        this.leaves = leaves;
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

    public String getPlantHealth() {
        return plantHealth;
    }

    public void setPlantHealth(String plantHealth) {
        this.plantHealth = plantHealth;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public String getFlower() {
        return flower;
    }

    public void setFlower(String flower) {
        this.flower = flower;
    }

    public String getLeaves() {
        return leaves;
    }

    public void setLeaves(String leaves) {
        this.leaves = leaves;
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
