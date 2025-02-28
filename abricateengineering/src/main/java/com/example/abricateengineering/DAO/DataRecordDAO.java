package com.example.abricateengineering.DAO;

import java.time.LocalDateTime;

public class DataRecordDAO {
     private LocalDateTime DateTime;
    private String formula;
    private Float[] tValues = new Float[20];
    private Float[] aValues = new Float[20];
    private String[] nValues = new String[20];
    
    public LocalDateTime getDateTime() {
        return DateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.DateTime = dateTime;
    }
    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
    public Float[] getTValues() {
        return tValues;
    }

    public void setTValues(Float[] tValues) {
        this.tValues = tValues;
    }

    public Float[] getAValues() {
        return aValues;
    }

    public void setAValues(Float[] aValues) {
        this.aValues = aValues;
    }

    public String[] getNValues() {
        return nValues;
    }

    public void setNValues(String[] nValues) {
        this.nValues = nValues;
    }
 
}