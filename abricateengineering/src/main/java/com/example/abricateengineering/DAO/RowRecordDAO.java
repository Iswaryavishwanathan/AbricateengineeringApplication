package com.example.abricateengineering.DAO;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RowRecordDAO {
    private LocalDateTime dateTime;
    private String formula;
    private String rowType;
    private Float[] Values = new Float[20];
   

   public RowRecordDAO(LocalDateTime dateTime, String rowType, String formula, Float[] Values) {
    this.dateTime = dateTime;
    this.rowType = rowType;
    this.formula = formula;
    this.Values = Values;

}

public RowRecordDAO() {
    this.Values = new Float[0];
   
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
    public Float[] getValues() {
        return Values;
    }
   
}
