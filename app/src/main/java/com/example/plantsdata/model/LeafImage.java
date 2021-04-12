package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class LeafImage {

    private Bitmap leafImage;
    private int plant_id;

    public LeafImage(Bitmap leafImage, int plant_id) {
        this.leafImage = leafImage;
        this.plant_id = plant_id;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public Bitmap getLeafImage() {
        return leafImage;
    }

    public void setLeafImage(Bitmap leafImage) {
        this.leafImage = leafImage;
    }

}
