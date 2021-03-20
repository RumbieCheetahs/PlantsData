package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class Neighbour {
    Bitmap image;
    String name;
    int occurrences;
    int plant_id;

    public Neighbour(Bitmap image, String name, int occurrences, int plant_id) {
        this.image = image;
        this.name = name;
        this.occurrences = occurrences;
        this.plant_id = plant_id;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }
}
