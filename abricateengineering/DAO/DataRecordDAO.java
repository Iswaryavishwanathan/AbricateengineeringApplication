package com.example.abricateengineering.DAO;

public class DataRecordDAO {
    private String formula;
    private Integer[] tValues = new Integer[24];
    private Integer[] aValues = new Integer[24];
    private String[] nValues = new String[24];
    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
    public Integer[] getTValues() {
        return tValues;
    }

    public void setTValues(Integer[] tValues) {
        this.tValues = tValues;
    }

    public Integer[] getAValues() {
        return aValues;
    }

    public void setAValues(Integer[] aValues) {
        this.aValues = aValues;
    }

    public String[] getNValues() {
        return nValues;
    }

    public void setNValues(String[] nValues) {
        this.nValues = nValues;
    }
}