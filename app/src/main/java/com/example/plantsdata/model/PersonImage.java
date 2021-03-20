package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class PersonImage {
    Bitmap personImage;
    String name;
    int plant_id;

    public PersonImage(Bitmap personImage, String name, int plant_id) {
        this.personImage = personImage;
        this.name = name;
        this.plant_id = plant_id;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public Bitmap getPersonImage() {
        return personImage;
    }

    public void setPersonImage(Bitmap personImage) {
        this.personImage = personImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
