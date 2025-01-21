package com.example.abricateengineering.Service;
import org.springframework.stereotype.Service;
import com.example.abricateengineering.DAO.DataRecordDAO;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    
        // Track the previous set values to detect changes
        Integer[] previousTValues = new Integer[24];
        Arrays.fill(previousTValues, null);
        for (DataRecord currentRecord : originalRecords) {
            LocalDateTime dateTime = currentRecord.getDateTime();
            String formula = currentRecord.getFormula(); 
           
         // Fetch the values for the T columns (T_01 to T_24)
        Integer[] tValues = new Integer[24];
            for (int j = 0; j < 24; j++) {
                tValues[j] = currentRecord.getTValue(j);  
            }
         // Fetch the material names (N_01 to N_24)
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
                materialNames[j] = null;
            }
        }
    
            // Fetch the achieved weights (A_01 to A_24)
        Integer[] achievedWeights = new Integer[24];
            for (int j = 0; j < 24; j++) {
                try {
                    String achievedWeightField = "a" + String.format("%02d", j + 1);
                    Field weightField = DataRecord.class.getDeclaredField(achievedWeightField);
                    weightField.setAccessible(true);
                    achievedWeights[j] = (Integer) weightField.get(currentRecord);
                } catch (Exception e) {
                    e.printStackTrace();
                    achievedWeights[j] = null;
                }
            }
    
        boolean setValueChanged = false;
            for (int j = 0; j < 24; j++) {
    if (!Objects.equals(previousTValues[j], tValues[j])) {
        setValueChanged = true;
        break;
    }
}
                if (setValueChanged) {
                Integer[] filteredTValues = Arrays.stream(tValues)
                .filter(Objects::nonNull) 
                .toArray(Integer[]::new);

                RowRecordDAO tValueRow = new RowRecordDAO(dateTime, "setWeight", formula, filteredTValues, null);
                formattedRecords.add(tValueRow);
                System.out.println("Set Weight Row: " + Arrays.toString(filteredTValues));
            }

            // Always print aValues
        Integer[] filteredAValues = Arrays.stream(achievedWeights)
            .filter(Objects::nonNull) 
            .toArray(Integer[]::new);
            RowRecordDAO aValueRow = new RowRecordDAO(dateTime, "achWeight", formula, null, filteredAValues);
            formattedRecords.add(aValueRow);
            System.out.println("Achieved Weight Row: " + Arrays.toString(filteredAValues));
            previousTValues = tValues.clone();
            }
            Map<String, Object> result = new HashMap<>();
            result.put("ColumnNames", new ArrayList<>(columnNamesSet)); 
            result.put("Records", formattedRecords);
            System.out.println("Total formatted records: " + formattedRecords.size());
            return result;
    }
    
}