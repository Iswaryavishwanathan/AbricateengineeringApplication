package com.example.abricateengineering.Service;

import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.DAO.MaterialReport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;
@Service
public class MaterialReportService {
    private static final int NUMBER_OF_VALUES = 24;

    public void processMaterialReports(List<DataRecordDAO> dataRecordDAOs, List<MaterialReport> materialReports) {
        dataRecordDAOs.forEach(dataRecordDAO -> processSingleMaterialReport(dataRecordDAO, materialReports));
    }

    public void processSingleMaterialReport(DataRecordDAO dataRecordDAO, List<MaterialReport> materialReports) {
        IntStream.range(0, NUMBER_OF_VALUES).forEach(i -> {
            String materialName = dataRecordDAO.getNValues()[i];
            Integer achWeight = dataRecordDAO.getAValues()[i];
            Integer setWeight = dataRecordDAO.getTValues()[i];

            if (materialName != null && achWeight != null && setWeight != null && (achWeight != 0 && setWeight != 0)) {
                if (materialIsPresent(materialName, materialReports)) {
                    changesInMaterialRecord(getIndexValue(materialName, materialReports), achWeight, setWeight, materialReports);
                } else {
                    addMaterialRecord(materialName, setWeight, achWeight, materialReports);
                }
            }
        });
    }

    private void changesInMaterialRecord(int index, int achWeight, int setWeight, List<MaterialReport> materialReports) {
        MaterialReport existingMaterialReport = materialReports.get(index);
        existingMaterialReport.setAchWeight(existingMaterialReport.getAchWeight() + achWeight);
        existingMaterialReport.setSetWeight(existingMaterialReport.getSetWeight() + setWeight);
    }

    private void addMaterialRecord(String name, int setWeight, int achWeight, List<MaterialReport> materialReports) {
        MaterialReport newMaterialReport = new MaterialReport(name, setWeight, achWeight);
        materialReports.add(newMaterialReport);
    }

    private boolean materialIsPresent(String materialName, List<MaterialReport> materialReports) {
        return materialReports.stream().anyMatch(mr -> mr.getMaterialName().equals(materialName));
    }

    private int getIndexValue(String materialName, List<MaterialReport> materialReports) {
        return IntStream.range(0, materialReports.size())
                .filter(i -> materialReports.get(i).getMaterialName().equals(materialName))
                .findFirst()
                .orElse(-1);
    }

}
