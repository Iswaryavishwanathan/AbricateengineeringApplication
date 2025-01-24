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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
        String formulaName = dataRecordDAO.getFormula();
        if (!isRecipeConsompsion(formulaName, recipeConsompsions)) {
            recipeConsompsions.add(new RecipeConsompsion(formulaName, new ArrayList<>()));
        }
        int index = getIndexValue(formulaName, recipeConsompsions);
        materialReportService.processSingleMaterialReport(dataRecordDAO, recipeConsompsions.get(index).getMaterialReport());
    }

    private boolean isRecipeConsompsion(String formulaName, List<RecipeConsompsion> recipeConsompsions) {
        for (RecipeConsompsion recipeConsompsion : recipeConsompsions) {
            if (recipeConsompsion.getFormulaName().equals(formulaName)) {
                return true;
            }
        }
        return false;
    }

    private int getIndexValue(String formulaName, List<RecipeConsompsion> recipeConsompsions) {
        for (int i = 0; i < recipeConsompsions.size(); i++) {
            if (recipeConsompsions.get(i).getFormulaName().equals(formulaName)) {
                return i;
            }
        }
        return -1;
    }

    public FormattedDataDAO getFormattedData(String startDateStr, String endDateStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateStr, dateFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, dateFormatter);

        List<DataRecord> originalRecords = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime);
        List<RowRecordDAO> formattedRecords = new ArrayList<>();
        Set<String> columnNamesSet = new HashSet<>();

        Float[] previousTValues = new Float[20];
        Arrays.fill(previousTValues, null);

        for (DataRecord currentRecord : originalRecords) {
            LocalDateTime dateTime = currentRecord.getDateTime();
            String formula = currentRecord.getFormula();

            Float[] tValues = new Float[20];
            for (int j = 0; j < 20; j++) {
                tValues[j] = currentRecord.getTValue(j);
            }

            String[] materialNames = new String[20];
            for (int j = 0; j < 20; j++) {
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

            boolean setValueChanged = false;
            for (int j = 0; j < 20; j++) {
                if (!Objects.equals(previousTValues[j], tValues[j])) {
                    setValueChanged = true;
                    break;
                }
            }

            if (setValueChanged) {
                List<Float> filteredTValuesList = new ArrayList<>();
                for (Float value : tValues) {
                    if (value != null) {
                        filteredTValuesList.add(value);
                    }
                }
                Float[] filteredTValues = filteredTValuesList.toArray(new Float[0]);
                formattedRecords.add(new RowRecordDAO(dateTime, "setWeight", formula, filteredTValues));
            }

            List<Float> filteredAValuesList = new ArrayList<>();
            for (Float value : achievedWeights) {
                if (value != null) {
                    filteredAValuesList.add(value);
                }
            }
            Float[] filteredAValues = filteredAValuesList.toArray(new Float[0]);
            formattedRecords.add(new RowRecordDAO(dateTime, "achWeight", formula, filteredAValues));

            previousTValues = tValues.clone();
        }

        List<String> columnNames = new ArrayList<>(columnNamesSet);
        return new FormattedDataDAO(columnNames, formattedRecords);
    }
    //added by abinav on  22/01/2025
    public List<DataRecordDAO> getDataAfterThisDate(String startDateStr,String endDateStr){
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateStr, dateFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, dateFormatter);

        List<DataRecordDAO> dataRecordDAOs = new ArrayList<>();
        List<DataRecord> dataRecords = dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime);
        for (DataRecord record : dataRecords) {
            dataRecordDAOs.add(dataDAOService.convertToDAO(record));
        }
        return dataRecordDAOs;   
    }
}
