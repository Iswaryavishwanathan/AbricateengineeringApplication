package com.example.abricateengineering.DAO;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RowRecordDAO {
    private LocalDateTime dateTime;
    private String formula;
    private String rowType;
    private Integer[] tValues = new Integer[24];
    private Float[] aValues = new Float[24]; // Changed to Float[]

    public RowRecordDAO(LocalDateTime dateTime, String rowType, String formula, Integer[] tValues, Float[] aValues) {
        this.dateTime = dateTime;
        this.rowType = rowType;
        this.formula = formula;
        this.tValues = tValues;
        this.aValues = aValues;
    }
    
    public RowRecordDAO() {
        this.tValues = new Integer[0];
        this.aValues = new Float[0]; // Changed to Float[]
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

    public Float[] getAValues() { // Changed to Float[]
        return aValues;
    }

    public void setAValues(Float[] aValues) { // Changed to Float[]
        this.aValues = aValues;
    }
}
