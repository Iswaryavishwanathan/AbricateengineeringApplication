package com.example.abricateengineering.Service;

import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.DAO.MaterialReport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialReportService {
    private static final int NUMBER_OF_VALUES = 20;

    public void processMaterialReports(List<DataRecordDAO> dataRecordDAOs, List<MaterialReport> materialReports) {
        Map<String, MaterialReport> materialReportMap = new HashMap<>();

        for (DataRecordDAO dataRecordDAO : dataRecordDAOs) {
            processSingleMaterialReport(dataRecordDAO, materialReportMap);
        }

        // Clear and update materialReports with optimized values
        materialReports.clear();
        materialReports.addAll(materialReportMap.values());
    }

    void processSingleMaterialReport(DataRecordDAO dataRecordDAO, Map<String, MaterialReport> materialReportMap) {
        for (int i = 0; i < NUMBER_OF_VALUES; i++) {
            String materialName = dataRecordDAO.getNValues()[i];
            Float achWeight = dataRecordDAO.getAValues()[i];
            Float setWeight = dataRecordDAO.getTValues()[i];

            if (materialName != null && achWeight != null && setWeight != null && (achWeight != 0 && setWeight != 0)) {
                if (materialReportMap.containsKey(materialName)) {
                    updateMaterialRecord(materialReportMap.get(materialName), achWeight, setWeight);
                } else {
                    materialReportMap.put(materialName, new MaterialReport(materialName, setWeight, achWeight));
                }
            }
        }
    }

    private void updateMaterialRecord(MaterialReport materialReport, float achWeight, float setWeight) {
        materialReport.setAchWeight(materialReport.getAchWeight() + achWeight);
        materialReport.setSetWeight(materialReport.getSetWeight() + setWeight);
    }
}
