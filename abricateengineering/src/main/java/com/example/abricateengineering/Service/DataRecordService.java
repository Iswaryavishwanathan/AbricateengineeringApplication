package com.example.abricateengineering.Service;

import org.springframework.stereotype.Service;
import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.DAO.MaterialReport;
import com.example.abricateengineering.DAO.RecipeConsompsion;
import com.example.abricateengineering.Repository.DataRecordRepository;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DataRecordService {

    private final DataRecordRepository dataRecordRepository;
    private final DataDAOService dataDAOService;
    private final MaterialReportService materialReportService;

    private List<MaterialReport> materialReports;
    private List<RecipeConsompsion> recipeConsompsions;

    public DataRecordService(DataRecordRepository dataRecordRepository, DataDAOService dataDAOService, MaterialReportService materialReportService) {
        this.dataRecordRepository = dataRecordRepository;
        this.dataDAOService = dataDAOService;
        this.materialReportService = materialReportService;
    }

    public List<MaterialReport> getMaterialReportBtwnReports(String startDateStr, String endDateStr) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
    LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
    LocalDateTime startDateTime = startDate.atStartOfDay();
    LocalDateTime endDateTime = endDate.atStartOfDay();

        List<DataRecordDAO> dataRecordDAOs = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime).stream()
                .map(dataDAOService::convertToDAO)
                .collect(Collectors.toList());
        materialReports = new ArrayList<>();
        materialReportService.processMaterialReports(dataRecordDAOs, materialReports);
        return materialReports;
    }

    public List<RecipeConsompsion> getRecipeConsompsions(String startDateStr, String endDateStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();
    
        List<DataRecordDAO> dataRecordDAOs =  dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime).stream()
                .map(dataDAOService::convertToDAO)
                .collect(Collectors.toList());
        recipeConsompsions = new ArrayList<>();
        dataRecordDAOs.forEach(dataRecordDAO -> processRecipeConsumption(dataRecordDAO, recipeConsompsions));
        return recipeConsompsions;
    }

    private void processRecipeConsumption(DataRecordDAO dataRecordDAO, List<RecipeConsompsion> recipeConsompsions) {
        String formulaName = dataRecordDAO.getFormula();
        if (!isRecipeConsompsion(formulaName, recipeConsompsions)) {
            recipeConsompsions.add(new RecipeConsompsion(formulaName, new ArrayList<>()));
        }
        int index = getIndexValue(formulaName, recipeConsompsions);
        materialReportService.processSingleMaterialReport(dataRecordDAO, recipeConsompsions.get(index).getMaterialReport());
    }

    private boolean isRecipeConsompsion(String formulaName, List<RecipeConsompsion> recipeConsompsions) {
        return recipeConsompsions.stream().anyMatch(recipeConsompsion -> recipeConsompsion.getFormulaName().equals(formulaName));
    }

    private int getIndexValue(String formulaName, List<RecipeConsompsion> recipeConsompsions) {
        return IntStream.range(0, recipeConsompsions.size())
                .filter(i -> recipeConsompsions.get(i).getFormulaName().equals(formulaName))
                .findFirst()
                .orElse(-1);
    }
    public Map<String, Object> getFormattedData(String startDateStr, String endDateStr) {
        LocalDateTime startDateTime = parseDateTime(startDateStr);
        LocalDateTime endDateTime = parseDateTime(endDateStr);
    
        // Fetch data records from the database
        List<DataRecordDAO> dataRecordDAOs = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime).stream()
                .map(dataDAOService::convertToDAO)
                .collect(Collectors.toList());
    
        // Prepare the result
        Map<String, Object> result = new HashMap<>();
        Set<String> columnNames = new HashSet<>();
    
        for (DataRecordDAO record : dataRecordDAOs) {
            // Add material names to the column set
            Collections.addAll(columnNames, record.getNValues());
        }
        result.put("ColumnName", new ArrayList<>(columnNames));
    
        List<Map<String, Object>> dataRows = new ArrayList<>();
        for (DataRecordDAO record : dataRecordDAOs) {
            // Use the exact dateTime from the database
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("dateTime", record.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            rowData.put("rowType", record.getRowType());
    
            List<Integer> values = new ArrayList<>();
            for (String materialName : columnNames) {
                int index = findMaterialIndex(record.getNValues(), materialName);
                if (index >= 0) {
                    int setWeight = record.getTValues()[index];
                    int achWeight = record.getAValues()[index];
                    values.add(setWeight);
                    values.add(achWeight);
                } else {
                    values.add(0); // Default value for missing materials
                }
            }
    
            rowData.put("values", values);
            dataRows.add(rowData);
        }
    
        result.put("data", dataRows);
        return result;
    }
    
    private LocalDateTime parseDateTime(String dateStr) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, dateFormatter).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd", e);
        }
    }

    private int findMaterialIndex(String[] materialArray, String materialName) {
        for (int i = 0; i < materialArray.length; i++) {
            if (materialArray[i] != null && materialArray[i].equals(materialName)) {
                return i;
            }
        }
        return -1;
    }
}