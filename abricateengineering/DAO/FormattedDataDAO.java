package com.example.abricateengineering.DAO;

import java.util.List;

public class FormattedDataDAO {
    private List<String> columnNames;
    private List<RowRecordDAO> records;

    public FormattedDataDAO(List<String> columnNames, List<RowRecordDAO> records) {
        this.columnNames = columnNames;
        this.records = records;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<RowRecordDAO> getRecords() {
        return records;
    }

    public void setRecords(List<RowRecordDAO> records) {
        this.records = records;
    }

}
