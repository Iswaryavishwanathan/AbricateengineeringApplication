package com.example.abricateengineering.DAO;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RowRecordDAO {
    private LocalDateTime dateTime;
    private String formula;
    private String rowType;
    private Integer[] tValues = new Integer[24];
    private Integer[] aValues = new Integer[24];

   public RowRecordDAO(LocalDateTime dateTime, String rowType, String formula, Integer[] tValues, Integer[] aValues) {
    this.dateTime = dateTime;
    this.rowType = rowType;
    this.formula = formula;
    this.tValues = tValues;
    this.aValues = aValues;
}

public RowRecordDAO() {
    this.tValues = new Integer[0];
    this.aValues = new Integer[0];
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
}
