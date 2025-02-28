package com.example.abricateengineering.Service;

import org.springframework.stereotype.Service;
import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.DAO.DropDownDetails;
import com.example.abricateengineering.DAO.FormattedDataDAO;
import com.example.abricateengineering.DAO.MaterialReport;
import com.example.abricateengineering.DAO.RecipeConsompsion;
import com.example.abricateengineering.DAO.RowRecordDAO;
import com.example.abricateengineering.Repository.DataRecordRepository;
import com.example.abricateengineering.entity.DataRecord;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateStr, dateFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, dateFormatter);

        List<DataRecordDAO> dataRecordDAOs = new ArrayList<>();
        List<DataRecord> dataRecords = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime);
        for (DataRecord record : dataRecords) {
            dataRecordDAOs.add(dataDAOService.convertToDAO(record));
        }

        materialReports = new ArrayList<>();
        materialReportService.processMaterialReports(dataRecordDAOs, materialReports);
        return materialReports;
    }

    public List<RecipeConsompsion> getRecipeConsompsions(String startDateStr, String endDateStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateStr, dateFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, dateFormatter);

        List<DataRecordDAO> dataRecordDAOs = new ArrayList<>();
        List<DataRecord> dataRecords = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime);
        for (DataRecord record : dataRecords) {
            dataRecordDAOs.add(dataDAOService.convertToDAO(record));
        }

        recipeConsompsions = new ArrayList<>();
        for (DataRecordDAO dataRecordDAO : dataRecordDAOs) {
            processRecipeConsumption(dataRecordDAO, recipeConsompsions);
        }
        return recipeConsompsions;
    }

    private void processRecipeConsumption(DataRecordDAO dataRecordDAO, List<RecipeConsompsion> recipeConsompsions) {
        Map<String, RecipeConsompsion> recipeConsompsionMap = new HashMap<>();
    
        // Populate the HashMap for quick lookups
        for (RecipeConsompsion rc : recipeConsompsions) {
            recipeConsompsionMap.put(rc.getFormulaName(), rc);
        }
    
        String formulaName = dataRecordDAO.getFormula();
    
        // If not present, create a new RecipeConsompsion
        RecipeConsompsion recipeConsompsion = recipeConsompsionMap.get(formulaName);
        if (recipeConsompsion == null) {
            recipeConsompsion = new RecipeConsompsion(formulaName,0, new ArrayList<>());
            recipeConsompsions.add(recipeConsompsion);
            recipeConsompsionMap.put(formulaName, recipeConsompsion);
        }
        recipeConsompsion.setNoOfBatches(recipeConsompsion.getNoOfBatches()+1);
        Map<String, MaterialReport> materialReportsMap = new HashMap<>();
    
        // Process material report
        for(MaterialReport mr : recipeConsompsion.getMaterialReport()){
            materialReportsMap.put(mr.getMaterialName(), mr);
        }
        materialReportService.processSingleMaterialReport(dataRecordDAO, materialReportsMap);
        recipeConsompsion.getMaterialReport().clear(); 
        recipeConsompsion.getMaterialReport().addAll(materialReportsMap.values());
        
    }

     public FormattedDataDAO getFormattedData(String startDateStr, String endDateStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateStr, dateFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, dateFormatter);
    
        List<DataRecord> originalRecords = dataRecordRepository.findRecordsSorted(startDateTime, endDateTime);
        List<RowRecordDAO> formattedRecords = new ArrayList<>();
        List<DropDownDetails> dropDownDetailsList = new ArrayList<>();
    
        // Map to track previous T values for each formula
        Map<String, Float[]> previousFormulaTValuesMap = new HashMap<>();
    
        for (DataRecord currentRecord : originalRecords) {
            LocalDateTime dateTime = currentRecord.getDateTime();
            String formula = currentRecord.getFormula();
    
            // Collect material names for this formula
            List<String> materialNamesList = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                try {
                    String materialNameField = "n" + String.format("%02d", j + 1);
                    Field materialField = DataRecord.class.getDeclaredField(materialNameField);
                    materialField.setAccessible(true);
                    String materialName = (String) materialField.get(currentRecord);
                    materialNamesList.add(materialName != null ? materialName : "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    
            // Add formula to drop-down list if not already added
            boolean formulaExists = false;
            for (DropDownDetails details : dropDownDetailsList) {
                if (details.getFormulaName().equals(formula)) {
                    formulaExists = true;
                    break;
                }
            }
            if (!formulaExists) {
                dropDownDetailsList.add(new DropDownDetails(formula, materialNamesList));
            }
    
            // Get T-values from current record
            Float[] tValues = new Float[20];
            for (int j = 0; j < 20; j++) {
                tValues[j] = currentRecord.getTValue(j);
            }
    
            // Get achieved weights
            Float[] achievedWeights = new Float[20];
            for (int j = 0; j < 20; j++) {
                try {
                    String achievedWeightField = "a" + String.format("%02d", j + 1);
                    Field weightField = DataRecord.class.getDeclaredField(achievedWeightField);
                    weightField.setAccessible(true);
                    achievedWeights[j] = (Float) weightField.get(currentRecord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    
            // Check if T-values have changed for this formula
            boolean setValueChanged = false;
            Float[] previousTValues = previousFormulaTValuesMap.get(formula);
    
            if (previousTValues == null) {
                setValueChanged = true; // First occurrence of formula, treat as a change
            } else {
                for (int j = 0; j < 20; j++) {
                    if (!Objects.equals(previousTValues[j], tValues[j])) {
                        setValueChanged = true;
                        break;
                    }
                }
            }
    
            // Add "setWeight" row only if T-values changed for this formula
            if (setValueChanged) {
                List<Float> filteredTValuesList = new ArrayList<>();
                for (int j = 0; j < 20; j++) {
                    if (tValues[j] != null) {
                        filteredTValuesList.add(tValues[j]);
                    }
                }
                Float[] filteredTValues = filteredTValuesList.toArray(new Float[0]);
    
                formattedRecords.add(new RowRecordDAO(dateTime, "SetWeight", formula, filteredTValues));
    
                // Update previous T-values for the formula
                previousFormulaTValuesMap.put(formula, tValues.clone());
            }
    
            // Always add "achWeight" row
            List<Float> filteredAValuesList = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                if (achievedWeights[j] != null) {
                    filteredAValuesList.add(achievedWeights[j]);
                }
            }
            Float[] filteredAValues = filteredAValuesList.toArray(new Float[0]);
    
            formattedRecords.add(new RowRecordDAO(dateTime, "AchWeight", formula, filteredAValues));
        }
    
        return new FormattedDataDAO(dropDownDetailsList, formattedRecords);
    }
    
    
    //added by abinav on  22/01/2025
    public List<DataRecordDAO> getDataAfterThisDate(String startDateStr,String endDateStr){
        LocalDateTime startDateTime =null;
        LocalDateTime endDateTime =null;
        
        try {
            // Include optional nanoseconds in the pattern
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.n]");
            startDateTime = LocalDateTime.parse(startDateStr.replace("\"", ""), dateFormatter);
            endDateTime = LocalDateTime.parse(endDateStr.replace("\"", ""), dateFormatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
            
            
        
        
        List<DataRecordDAO> dataRecordDAOs = new ArrayList<>();
        List<DataRecord> dataRecords = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime);
        for (DataRecord record : dataRecords) {
            dataRecordDAOs.add(dataDAOService.convertToDAO(record));
        }
        return dataRecordDAOs;

        
    }
}
