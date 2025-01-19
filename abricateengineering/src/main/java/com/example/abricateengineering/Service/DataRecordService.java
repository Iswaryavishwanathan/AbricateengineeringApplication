package com.example.abricateengineering.Service;

import org.springframework.stereotype.Service;
import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.DAO.MaterialReport;
import com.example.abricateengineering.DAO.RecipeConsompsion;
import com.example.abricateengineering.Repository.DataRecordRepository;
import com.example.abricateengineering.entity.DataRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;

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
    public List<DataRecordDAO> getFormattedData(String startDateStr, String endDateStr) {
        // Define the DateTimeFormatter based on the date string format (yyyy-MM-dd)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Convert the String parameters to LocalDate objects
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        // Convert LocalDate to LocalDateTime (start of the day)
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();
        // Fetch the records from the repository based on the date range
        List<DataRecord> originalRecords = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime);
        // Initialize the list to hold formatted DataRecordDAO results
        List<DataRecordDAO> formattedRecords = new ArrayList<>();
        // Iterate through the records and format them as needed
        for (DataRecord record : originalRecords) {
            LocalDateTime dateTime = record.getDateTime();
            String[] materialName = record.getNValues();
            Integer[] setWeight = record.getTValues();
            Integer[] achWeight = record.getAValues();
            // Track previous setWeight for each material
            Integer[] previousSetWeights = new Integer[materialName.length];
            for (int i = 0; i < materialName.length; i++) {
                if (materialName[i] != null) {
                    // Compare current setWeight with the previous one
                    if (previousSetWeights[i] == null || !previousSetWeights[i].equals(setWeight[i])) {
                        // Create a DataRecordDAO for setWeight when it changes
                        DataRecordDAO setWeightRecord = new DataRecordDAO();
                        setWeightRecord.setDateTime(dateTime);
                        setWeightRecord.setRowType("setWeight");
                        setWeightRecord.setNValues(new String[]{materialName[i]});
                        setWeightRecord.setTValues(new Integer[]{setWeight[i]});
                        setWeightRecord.setAValues(new Integer[]{0}); // No achieved weight for setWeight
                        formattedRecords.add(setWeightRecord);
                        // Update previous setWeight
                        previousSetWeights[i] = setWeight[i];
                    }
                    // Create a DataRecordDAO for achievedWeight
                    DataRecordDAO achWeightRecord = new DataRecordDAO();
                    achWeightRecord.setDateTime(dateTime);
                    achWeightRecord.setRowType("achWeight");
                    achWeightRecord.setNValues(new String[]{materialName[i]});
                    achWeightRecord.setTValues(new Integer[]{0}); // No set weight for achievedWeight
                    achWeightRecord.setAValues(new Integer[]{achWeight[i]});
                    formattedRecords.add(achWeightRecord);
                }
            }
        }
        return formattedRecords;
    }
    // Method to get formatted data
    // public List<DataRecordDAO> getFormattedData(String startDateStr, String endDateStr) {
    //     // Define the DateTimeFormatter based on the date string format (yyyy-MM-dd)
    //     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //     // Convert the String parameters to LocalDate objects
    //     LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
    //     LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
    //     // Convert LocalDate to LocalDateTime (start of the day)
    //     LocalDateTime startDateTime = startDate.atStartOfDay();
    //     LocalDateTime endDateTime = endDate.atStartOfDay();
        
    //     // Fetch the records from the repository based on the date range
    //     List<DataRecord> originalRecords = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime);
        
    //     // Initialize the list to hold formatted DataRecordDAO results
    //     List<DataRecordDAO> formattedRecords = new ArrayList<>();
        
    //     // Track previous setWeight for each material
    //     Integer[] previousSetWeights = new Integer[0];

    //     // Iterate through the records and format them as needed
    //     for (DataRecord record : originalRecords) {
    //         LocalDateTime dateTime = record.getDateTime();
    //         String[] materialName = record.getNValues();
    //         Integer[] setWeight = record.getTValues();
    //         Integer[] achWeight = record.getAValues();

    //         for (int i = 0; i < materialName.length; i++) {
    //             if (materialName[i] != null) {
    //                 // Compare current setWeight with the previous one
    //                 if (previousSetWeights[i] == null || !previousSetWeights[i].equals(setWeight[i])) {
    //                     // Create a DataRecordDAO for setWeight when it changes
    //                     DataRecordDAO setWeightRecord = new DataRecordDAO(
    //                         dateTime, "setWeight", new String[]{materialName[i]}, new Integer[]{setWeight[i]}, new Integer[]{0}
    //                     );
    //                     formattedRecords.add(setWeightRecord);
                        
    //                     // Update previous setWeight
    //                     previousSetWeights[i] = setWeight[i];
    //                 }

    //                 // Create a DataRecordDAO for achievedWeight
    //                 DataRecordDAO achWeightRecord = new DataRecordDAO(
    //                     dateTime, "achWeight", new String[]{materialName[i]}, new Integer[]{0}, new Integer[]{achWeight[i]}
    //                 );
    //                 formattedRecords.add(achWeightRecord);
    //             }
    //         }
    //     }
    //     return formattedRecords;
    // }
}