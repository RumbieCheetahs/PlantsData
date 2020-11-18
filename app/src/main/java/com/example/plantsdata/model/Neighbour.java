package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class Neighbour {
    Bitmap image;
    String name;
    int occurrences;

    public Neighbour(Bitmap image, String name, int occurrences) {
        this.image = image;
        this.name = name;
        this.occurrences = occurrences;
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
