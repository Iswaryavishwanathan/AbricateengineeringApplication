package com.example.abricateengineering.Controller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import com.example.abricateengineering.Service.ExcelExportService;
import com.example.abricateengineering.Service.SendLastDateService;

@RestController
@RequestMapping("/api/data")
public class DataRecordController {
    @Autowired
    
    private final DataRecordService dataRecordService;
    private final DataReceiveService dataReceiveService; 
    private final SendLastDateService sendLastDateService;
    private final ExcelExportService excelExportService;

    public DataRecordController(DataRecordService dataRecordService, DataReceiveService dataReceiveService, SendLastDateService sendLastDateService, ExcelExportService excelExportService) {
        this.dataRecordService = dataRecordService;
        this.dataReceiveService = dataReceiveService; 
        this.sendLastDateService = sendLastDateService; 
        this.excelExportService = excelExportService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if ("admin".equals(username) && "admin".equals(password)) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password.");
        }
    }
    @GetMapping("/material-report")
    public List<MaterialReport> getMaterialReportsBetweenDates(
            @RequestParam("startDate") String startDate, 
            @RequestParam("endDate") String endDate) {
        return dataRecordService.getMaterialReportBtwnReports(startDate, endDate);
    }
@GetMapping("/material-report/excel")
    public ResponseEntity<InputStreamResource> exportMaterialReportToExcel(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try {
            List<MaterialReport> materialReports = dataRecordService.getMaterialReportBtwnReports(startDate, endDate);
            List<RecipeConsompsion> recipeConsumptions = dataRecordService.getRecipeConsompsions(startDate, endDate);
            ByteArrayInputStream bais = excelExportService.exportMaterialReportsToExcel(materialReports,recipeConsumptions ,startDate, endDate);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=Material_Report_" + startDate + "_to_" + endDate + ".xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(bais));
        } catch (IOException e) {
            // Handle IOException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/recipe-consumptions")
    public List<RecipeConsompsion> getRecipeConsompsions(
        @RequestParam("startDate") String startDate, 
        @RequestParam("endDate") String endDate) {
        return dataRecordService.getRecipeConsompsions(startDate, endDate);
    }
    @GetMapping("/recipe-consumptions/excel")
    public ResponseEntity<InputStreamResource> exportRecipeConsumptionsToExcel(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try {
            List<RecipeConsompsion> recipeConsumptions = dataRecordService.getRecipeConsompsions(startDate, endDate);
            ByteArrayInputStream bais = excelExportService.exportRecipeConsumptionsToExcel(recipeConsumptions, startDate, endDate);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=Recipe_Consumption_" + startDate + "_to_" + endDate + ".xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(bais));
        } catch (IOException e) {
            // Handle IOException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/batch-report/excel")
    public ResponseEntity<InputStreamResource> batchReportToExcel(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate)  {
                try {
                    List<RecipeConsompsion> recipeConsumptions = dataRecordService.getRecipeConsompsions(startDate, endDate);
                    ByteArrayInputStream bais = excelExportService.exportRecipeConsumptionsToExcel(recipeConsumptions, startDate, endDate);
        
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Disposition", "attachment; filename=Recipe_Consumption_" + startDate + "_to_" + endDate + ".xlsx");
        
                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(new InputStreamResource(bais));
                } catch (IOException e) {
                    // Handle IOException
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                } 
    }

    @GetMapping("/BatchReport")
    public  FormattedDataDAO getFormattedData(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return dataRecordService.getFormattedData(startDate, endDate);
    }

    @PostMapping("/receive-data")
    public ResponseEntity<Map<String, String>> receiveData(@RequestBody List<DataRecordDAO> dataRecordDAOs) {
        try {
            dataReceiveService.processReceivedData(dataRecordDAOs);
            dataReceiveService.deleteOldRecords();
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Data processed successfully, old records deleted.");
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
        System.out.println("latest");
        if (latestRecord != null) {
            return ResponseEntity.ok(latestRecord);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
