package com.example.abricateengineering.Service;
import org.springframework.stereotype.Service;
import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.DAO.FormattedDataDAO;
import com.example.abricateengineering.DAO.MaterialReport;
import com.example.abricateengineering.DAO.RecipeConsompsion;
import com.example.abricateengineering.DAO.RowRecordDAO;
import com.example.abricateengineering.Repository.DataRecordRepository;
import com.example.abricateengineering.entity.DataRecord;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
// import java.util.Objects;
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
    public FormattedDataDAO getFormattedData(String startDateStr, String endDateStr) { 
        System.out.println("Received getFormattedData request with startDate: " + startDateStr + " and endDate: " + endDateStr);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();
        System.out.println("Fetching records from " + startDateTime + " to " + endDateTime);
    
        List<DataRecord> originalRecords = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime);
        System.out.println("Fetched " + originalRecords.size() + " records for formatting.");
    
        List<RowRecordDAO> formattedRecords = new ArrayList<>();
        Set<String> columnNamesSet = new HashSet<>();
    
        Integer[] previousTValues = new Integer[24];
        Arrays.fill(previousTValues, null);  // Initialize to null instead of zero to track if a value has changed
    
        for (DataRecord currentRecord : originalRecords) {
            LocalDateTime dateTime = currentRecord.getDateTime();
            String formula = currentRecord.getFormula();
            // Fetch T values
Integer[] tValues = new Integer[24];
for (int j = 0; j < 24; j++) {
    Integer tValue = currentRecord.getTValue(j);  // Ensure this method is retrieving the correct value
    if (tValue == null) {
        tValues[j] = 0;  // Set to 0 if null
    } else {
        tValues[j] = tValue;  // Use the fetched value
    }
    System.out.println("TValue[" + j + "]: " + tValues[j]);  // Print the fetched value
}

// Fetch and print T_01 specifically
Integer tValueFirst = currentRecord.getTValue(0);  // Fetch T_01 specifically
System.out.println("Fetched T_01 directly: " + (tValueFirst != null ? tValueFirst : 0));  // Print the T_01 value

    
            // Fetch material names (N_01 to N_24)
            String[] materialNames = new String[24];
            for (int j = 0; j < 24; j++) {
                try {
                    String materialNameField = "n" + String.format("%02d", j + 1);
                    Field materialField = DataRecord.class.getDeclaredField(materialNameField);
                    materialField.setAccessible(true);
                    materialNames[j] = (String) materialField.get(currentRecord);
                    if (materialNames[j] != null && !materialNames[j].isEmpty()) {
                        columnNamesSet.add(materialNames[j]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    
            // Fetch achieved weights (A_01 to A_24) as Float
            Float[] achievedWeights = new Float[24];
            for (int j = 0; j < 24; j++) {
                try {
                    String achievedWeightField = "a" + String.format("%02d", j + 1);
                    Field weightField = DataRecord.class.getDeclaredField(achievedWeightField);
                    weightField.setAccessible(true);
                    Integer weightValue = (Integer) weightField.get(currentRecord);
                    if (weightValue != null) {
                        achievedWeights[j] = weightValue.floatValue(); // Convert Integer to Float
                    } else {
                        achievedWeights[j] = 0f; // Set default value to 0 if no value
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    
            boolean setValueChanged = false;
            for (int j = 0; j < 24; j++) {
                if (previousTValues[j] == null || !previousTValues[j].equals(tValues[j])) {
                    setValueChanged = true;
                    break;
                }
            }
    
            // Add "setWeight" row if T values changed
            if (setValueChanged) {
                Integer[] filteredTValues = Arrays.stream(tValues)
                    .toArray(Integer[]::new);
    
                formattedRecords.add(new RowRecordDAO(dateTime, "setWeight", formula, filteredTValues, null));
                System.out.println("Set Weight Row: " + Arrays.toString(filteredTValues));
            }
    
            // Convert Integer[] to Float[] for achieved weights
            Float[] filteredAValues = Arrays.stream(achievedWeights)
                .map(i -> i != null ? i : 0f)  // Replace null with 0 if needed
                .toArray(Float[]::new); // Collect into a Float[] array
    
            formattedRecords.add(new RowRecordDAO(dateTime, "achWeight", formula, null, filteredAValues));
    
            previousTValues = tValues.clone(); // Update previousTValues for next comparison
        }
    
        List<String> columnNames = new ArrayList<>(columnNamesSet); // Convert Set to List
        System.out.println("Total formatted records: " + formattedRecords.size());
    
        // Return the final data encapsulated in FormattedDataDAO
        return new FormattedDataDAO(columnNames, formattedRecords);
    }
    
    

    
}