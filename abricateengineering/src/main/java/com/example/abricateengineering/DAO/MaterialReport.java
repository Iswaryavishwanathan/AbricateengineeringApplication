package com.example.abricateengineering.DAO;

public class MaterialReport {
    private String materialName;
    private float setWeight;
    private float achWeight;
    public MaterialReport(String materialName, float setWeight, float achWeight) {
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
    public float getSetWeight() {
        return setWeight;
    }
    public void setSetWeight(float setWeight) {
        this.setWeight = setWeight;
    }
    public float getAchWeight() {
        return achWeight;
    }
    public void setAchWeight(float achWeight) {
        this.achWeight = achWeight;
    }

}
