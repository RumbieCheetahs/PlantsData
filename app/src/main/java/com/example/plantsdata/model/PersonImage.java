package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class PersonImage {
    Bitmap personImage;
    String name;

    public PersonImage(Bitmap personImage, String name) {
        this.personImage = personImage;
        this.name = name;
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
