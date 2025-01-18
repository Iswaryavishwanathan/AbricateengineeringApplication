package com.example.abricateengineering.Controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.abricateengineering.DAO.MaterialReport;
import com.example.abricateengineering.DAO.RecipeConsompsion;
import com.example.abricateengineering.Service.DataRecordService;


import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataRecordController {

    private final DataRecordService dataRecordService;

    public DataRecordController(DataRecordService dataRecordService) {
        this.dataRecordService = dataRecordService;
    }

    @GetMapping("/material-report")
    public List<MaterialReport> getMaterialReportsBetweenDates(
            @RequestParam("startDate") String startDate, 
            @RequestParam("endDate") String endDate) {
        return dataRecordService.getMaterialReportBtwnReports(startDate, endDate);
    }

    @GetMapping("/recipe-consumptions")
    public List<RecipeConsompsion> getRecipeConsompsions(
        @RequestParam("startDate") String startDate, 
        @RequestParam("endDate") String endDate) {
        return dataRecordService.getRecipeConsompsions(startDate, endDate);
    }
   @GetMapping("/formatted-data")
    public ResponseEntity<Map<String, Object>> getFormattedData(
        @RequestParam("startDate") String startDate, 
        @RequestParam("endDate") String endDate) {
        Map<String, Object> result = dataRecordService.getFormattedData(startDate, endDate);
        return ResponseEntity.ok(result);
    }
}