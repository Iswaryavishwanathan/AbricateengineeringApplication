package com.example.abricateengineering.DAO;

import java.util.List;

public class FormattedDataDAO {
    private List<DropDownDetails> dropDownDetails;
    private List<RowRecordDAO> records;

    // Constructor
    public FormattedDataDAO(List<DropDownDetails> dropDownDetails, List<RowRecordDAO> records) {
        this.dropDownDetails = dropDownDetails;
        this.records = records;
    }

    // Getter and Setter methods
    public List<DropDownDetails> getDropDownDetails() {
        return dropDownDetails;
    }

    public void setDropDownDetails(List<DropDownDetails> dropDownDetails) {
        this.dropDownDetails = dropDownDetails;
    }

    public List<RowRecordDAO> getRecords() {
        return records;
    }

    public void setRecords(List<RowRecordDAO> records) {
        this.records = records;
    }
}

