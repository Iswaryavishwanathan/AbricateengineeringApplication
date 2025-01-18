package com.example.abricateengineering.DAO;

import java.time.LocalDateTime;

import com.example.abricateengineering.entity.DataRecord;

public class DataRecordDAO extends DataRecord {
    private String formula;
    private String rowType;
    private LocalDateTime dateTime;
    private Integer[] tValues = new Integer[24];
    private Integer[] aValues = new Integer[24];
    private String[] nValues = new String[24];

    // Default constructor
    public DataRecordDAO() {
        // Initialize dateTime to current time if not provided
        this.dateTime = LocalDateTime.now();
    }

   
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    

    public String getRowType() {
        return rowType;
    }

    public void setRowType(String rowType) {
        this.rowType = rowType;
    }

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
