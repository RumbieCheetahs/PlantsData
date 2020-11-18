package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class TreeImage {

    private Bitmap treeImage;
    private String treeName;
    private String date;

    public TreeImage(Bitmap treeImage, String treeName, String date) {
        this.treeImage = treeImage;
        this.treeName = treeName;
        this.date = date;
    }

    public Bitmap getTreeImage() {
        return treeImage;
    }

    public void setTreeImage(Bitmap treeImage) {
        this.treeImage = treeImage;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
