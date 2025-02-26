package com.example.abricateengineering.DAO;

import java.util.List;

public class DropDownDetails {
    private String formulaName;
    private List<String> materialNames;

    // Constructor
    public DropDownDetails(String formulaName, List<String> materialNames) {
        this.formulaName = formulaName;
        this.materialNames = materialNames;
    }

    // Getter and Setter methods
    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public List<String> getMaterialNames() {
        return materialNames;
    }

    public void setMaterialNames(List<String> materialNames) {
        this.materialNames = materialNames;
    }
}
