package com.example.plantsdata.model;

public class PlantStatus {

    private int plant_id;
    private Double treeHeight;
    private Double treeCircumference;
    private int treeStems;
    private String insectTypes;
    private String plantHealth;
    private String fruit;
    private String flower;
    private String leaves;

    public PlantStatus(int plant_id, Double treeHeight, Double treeCircumference, int treeStems, String insectTypes, String plantHealth, String fruit, String flower, String leaves) {
        this.plant_id = plant_id;
        this.treeHeight = treeHeight;
        this.treeCircumference = treeCircumference;
        this.treeStems = treeStems;
        this.insectTypes = insectTypes;
        this.plantHealth = plantHealth;
        this.fruit = fruit;
        this.flower = flower;
        this.leaves = leaves;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
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
