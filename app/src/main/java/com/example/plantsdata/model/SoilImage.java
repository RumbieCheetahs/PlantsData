package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class SoilImage {

    Bitmap soil;
    String plantHealth;
    String fruit;
    String flower;
    String leaves;

    public SoilImage(Bitmap soil, String plantHealth, String fruit, String flower, String leaves) {
        this.soil = soil;
        this.plantHealth = plantHealth;
        this.fruit = fruit;
        this.flower = flower;
        this.leaves = leaves;
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
}
