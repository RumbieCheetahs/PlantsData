package com.example.plantsdata.model;

import android.graphics.Bitmap;

public class LeafImage {

    private Bitmap leafImage;
    private Double treeHeight;
    private Double treeCircumference;
    private int treeStems;
    private String insectTypes;

    public LeafImage(Bitmap leafImage, Double treeHeight, Double treeCircumference, int treeStems, String insectTypes) {
        this.leafImage = leafImage;
        this.treeHeight = treeHeight;
        this.treeCircumference = treeCircumference;
        this.treeStems = treeStems;
        this.insectTypes = insectTypes;
    }

    public Bitmap getLeafImage() {
        return leafImage;
    }

    public void setLeafImage(Bitmap leafImage) {
        this.leafImage = leafImage;
    }

    public Double getTreeHeight() {
        return treeHeight;
    }

    public void setTreeHeight(Double treeHeight) {
        this.treeHeight = treeHeight;
    }

    public Double getTreeCircumference() {
        return treeCircumference;
    }

    public void setTreeCircumference(Double treeCircumference) {
        this.treeCircumference = treeCircumference;
    }

    public int getTreeStems() {
        return treeStems;
    }

    public void setTreeStems(int treeStems) {
        this.treeStems = treeStems;
    }

    public String getInsectTypes() {
        return insectTypes;
    }

    public void setInsectTypes(String insectTypes) {
        this.insectTypes = insectTypes;
    }
}
