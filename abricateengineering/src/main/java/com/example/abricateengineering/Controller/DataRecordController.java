package com.example.abricateengineering.Controller;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.DAO.FormattedDataDAO;
import com.example.abricateengineering.DAO.MaterialReport;
import com.example.abricateengineering.DAO.RecipeConsompsion;
import com.example.abricateengineering.Service.DataReceiveService;
import com.example.abricateengineering.Service.DataRecordService;
import com.example.abricateengineering.Service.SendLastDateService;

@RestController
@RequestMapping("/api/data")
public class DataRecordController {
    @Autowired
    
    private final DataRecordService dataRecordService;
    private final DataReceiveService dataReceiveService; 
    private final SendLastDateService sendLastDateService;

    public DataRecordController(DataRecordService dataRecordService, DataReceiveService dataReceiveService, SendLastDateService sendLastDateService) {
        this.dataRecordService = dataRecordService;
        this.dataReceiveService = dataReceiveService; 
        this.sendLastDateService = sendLastDateService; 
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
    @GetMapping("/formattedData")
    public  FormattedDataDAO getFormattedData(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return dataRecordService.getFormattedData(startDate, endDate);
    }

    @PostMapping("/receive-data")
public ResponseEntity<Map<String, String>> receiveData(@RequestBody List<DataRecordDAO> dataRecordDAOs) {
    try {
        dataReceiveService.processReceivedData(dataRecordDAOs);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Data processed successfully");
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(Map.of("status", "error", "message", e.getMessage()));
    }
}


    @GetMapping("/latest")
    public ResponseEntity<LocalDateTime> getLatestRecord() {
        LocalDateTime latestRecord = sendLastDateService.getLastRecord();
        if (latestRecord != null) {
            return ResponseEntity.ok(latestRecord);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
