package com.example.abricateengineering.DAO;

public class MaterialReport {
    private String materialName;
    private int setWeight;
    private int achWeight;
    public MaterialReport(String materialName, int setWeight, int achWeight) {
        this.materialName = materialName;
        this.setWeight = setWeight;
        this.achWeight = achWeight;
    }
    
    public String getMaterialName() {
        return materialName;
    }
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    public int getSetWeight() {
        return setWeight;
    }
    public void setSetWeight(int setWeight) {
        this.setWeight = setWeight;
    }
    public int getAchWeight() {
        return achWeight;
    }
    public void setAchWeight(int achWeight) {
        this.achWeight = achWeight;
    }

}
