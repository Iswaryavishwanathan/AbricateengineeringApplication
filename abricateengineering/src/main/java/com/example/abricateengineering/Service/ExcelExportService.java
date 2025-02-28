package com.example.abricateengineering.Service;

import com.example.abricateengineering.DAO.MaterialReport;
import com.example.abricateengineering.DAO.RecipeConsompsion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExcelExportService {

    private static final int START_COLUMN = 5; // Column 'F' has index 5

    public ByteArrayInputStream exportRecipeConsumptionsToExcel(List<RecipeConsompsion> recipeConsumptions, String startDateTime, String endDateTime) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        CellStyle titleStyle = createTitleCellStyle(workbook);
        CellStyle headerStyle = createHeaderCellStyle(workbook);
        CellStyle cellStyle = createCellStyle(workbook);
        CellStyle leftAlignedStyle = createLeftAlignedCellStyle(workbook);

        Set<String> sheetNames = new HashSet<>();
        for (RecipeConsompsion recipeConsumption : recipeConsumptions) {
            String sheetName = getUniqueSheetName(sanitizeSheetName(recipeConsumption.getFormulaName()), sheetNames);
            Sheet sheet = workbook.createSheet(sheetName);

            createCompanyNameRow(sheet, titleStyle);
            createReportTitleRow(sheet, titleStyle);
            createDateRangeRow(sheet, startDateTime, endDateTime, titleStyle);
            createHeaderRow(sheet, headerStyle);

            int rowNum = 4; // Start after headers
            int serialNo = 1;
            for (MaterialReport material : recipeConsumption.getMaterialReport()) {
                Row row = sheet.createRow(rowNum++);
                createDataRow(row, serialNo++, material, cellStyle, leftAlignedStyle);
            }

            createTotalRow(sheet, rowNum, recipeConsumption.getMaterialReport(), cellStyle);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream exportMaterialReportsToExcel(List<MaterialReport> materialReports, List<RecipeConsompsion> recipeConsumptions, String startDate, String endDate) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        CellStyle titleStyle = createTitleCellStyle(workbook);
        CellStyle headerStyle = createHeaderCellStyle(workbook);
        CellStyle cellStyle = createCellStyle(workbook);
        CellStyle leftAlignedStyle = createLeftAlignedCellStyle(workbook);

        Sheet sheet = workbook.createSheet("Material Reports");
        createCompanyNameRow(sheet, titleStyle);
        createReportTitleRow(sheet, titleStyle);
        createDateRangeRow(sheet, startDate, endDate, titleStyle);
        createHeaderRow(sheet, headerStyle);

        int rowNum = 4; // Start after headers
        int serialNo = 1;
        for (MaterialReport material : materialReports) {
            Row row = sheet.createRow(rowNum++);
            createDataRow(row, serialNo++, material, cellStyle, leftAlignedStyle);
        }

        createTotalRow(sheet, rowNum, materialReports, cellStyle);

        // Batch Report Sheet
        Sheet batchReportSheet = workbook.createSheet("Batch Report");
        createCompanyNameRow(batchReportSheet, titleStyle);
        createBatchReportTitleRow(batchReportSheet, titleStyle);
        createDateRangeRow(batchReportSheet, startDate, endDate, titleStyle);
        createBatchHeaderRow(batchReportSheet, headerStyle);
        rowNum = 4; // Start after headers
        serialNo = 1;
    
        int totalNoOfBatches = 0;
        int totalSetWeight = 0;
        int totalAchWeight = 0;
    
        for (RecipeConsompsion recipeConsompsion : recipeConsumptions) {
            int totalRecipeSetWeight = 0;
            int totalRecipeAchWeight = 0;
            for (MaterialReport materialReport : recipeConsompsion.getMaterialReport()) {
                totalRecipeSetWeight += materialReport.getSetWeight();
                totalRecipeAchWeight += materialReport.getAchWeight(); // Corrected to AchWeight
            }
            totalNoOfBatches += recipeConsompsion.getNoOfBatches();
            totalSetWeight += totalRecipeSetWeight;
            totalAchWeight += totalRecipeAchWeight;
    
            Row row = batchReportSheet.createRow(rowNum++);
            createRecipeDataRow(row, recipeConsompsion.getFormulaName(), serialNo++, recipeConsompsion.getNoOfBatches(), totalRecipeSetWeight, totalRecipeAchWeight, cellStyle, leftAlignedStyle);
        }
    
        createBatchTotalRow(batchReportSheet, rowNum, totalNoOfBatches, totalSetWeight, totalAchWeight, cellStyle);
    
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
    
    private CellStyle createTitleCellStyle(Workbook workbook) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 20);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return titleStyle;
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        return headerStyle;
    }

    private CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    private CellStyle createLeftAlignedCellStyle(Workbook workbook) {
        CellStyle leftAlignedStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        leftAlignedStyle.setFont(font);
        leftAlignedStyle.setAlignment(HorizontalAlignment.LEFT);
        leftAlignedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        leftAlignedStyle.setBorderBottom(BorderStyle.THIN);
        leftAlignedStyle.setBorderTop(BorderStyle.THIN);
        leftAlignedStyle.setBorderLeft(BorderStyle.THIN);
        leftAlignedStyle.setBorderRight(BorderStyle.THIN);
        return leftAlignedStyle;
    }

    private void createCompanyNameRow(Sheet sheet, CellStyle titleStyle) {
        Row companyNameRow = sheet.createRow(0);
        Cell companyNameCell = companyNameRow.createCell(START_COLUMN);
        companyNameCell.setCellValue("GEM ULTRA PRIVATE LTD- Feed Division");
        companyNameCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, START_COLUMN, START_COLUMN + 4));
    }

    private void createReportTitleRow(Sheet sheet, CellStyle titleStyle) {
        Row reportTitleRow = sheet.createRow(1);
        Cell reportTitleCell = reportTitleRow.createCell(START_COLUMN);
        reportTitleCell.setCellValue("Production Material consumption report");
        reportTitleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, START_COLUMN, START_COLUMN + 4));
    }
    private void createBatchReportTitleRow(Sheet sheet, CellStyle titleStyle) {
        Row reportTitleRow = sheet.createRow(1);
        Cell reportTitleCell = reportTitleRow.createCell(START_COLUMN);
        reportTitleCell.setCellValue("Batch Report");
        reportTitleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, START_COLUMN, START_COLUMN + 4));
    }

    private void createDateRangeRow(Sheet sheet, String startDateTime, String endDateTime, CellStyle titleStyle) {
        Row dateRangeRow = sheet.createRow(2);
        Cell dateRangeCell = dateRangeRow.createCell(START_COLUMN);
        dateRangeCell.setCellValue("From: " + formatDate(startDateTime) + " To: " + formatDate(endDateTime));
        dateRangeCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, START_COLUMN, START_COLUMN + 4));
    }

    private String formatDate(String dateTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTime; // Return original dateTime if parsing fails
        }
    }

    private void createHeaderRow(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(3);
        String[] headers = {"S.No", "Material Name", "Set Weight", "Actual Weight", "Weight Difference"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(START_COLUMN + i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(START_COLUMN + i, 6000); // Adjust column width for better visibility
        }
    }
    private void createBatchHeaderRow(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(3);
        String[] headers = {"S.No", "Receipe Name","No of Batches", "Set Weight", "Actual Weight", "Weight Difference"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(START_COLUMN + i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(START_COLUMN + i, 6000); // Adjust column width for better visibility
        }
    }

    private void createRecipeDataRow(Row row, String recipeName, int serialNo, int noOfBatches, int totalRecipeSetWeight, int totalRecipeAchWeight, CellStyle cellStyle, CellStyle leftAlignedStyle) {
        Cell cell0 = row.createCell(START_COLUMN);
        cell0.setCellValue(serialNo);
        cell0.setCellStyle(cellStyle);

        Cell cell1 = row.createCell(START_COLUMN + 1);
        cell1.setCellValue(recipeName);
        cell1.setCellStyle(leftAlignedStyle);

        Cell cell2 = row.createCell(START_COLUMN + 2);
        cell2.setCellValue(noOfBatches);
        cell2.setCellStyle(cellStyle);

        Cell cell3 = row.createCell(START_COLUMN + 3);
        cell3.setCellValue(totalRecipeSetWeight);
        cell3.setCellStyle(cellStyle);

        Cell cell4 = row.createCell(START_COLUMN + 4);
        cell4.setCellValue(totalRecipeAchWeight);
        cell4.setCellStyle(cellStyle);

        Cell cell5 = row.createCell(START_COLUMN + 5);
        cell5.setCellValue(totalRecipeSetWeight - totalRecipeAchWeight);
        cell5.setCellStyle(cellStyle);
    }

    private void createDataRow(Row row, int serialNo, MaterialReport material, CellStyle cellStyle, CellStyle leftAlignedStyle) {
        Cell cell0 = row.createCell(START_COLUMN);
        cell0.setCellValue(serialNo);
        cell0.setCellStyle(cellStyle);

        Cell cell1 = row.createCell(START_COLUMN + 1);
        cell1.setCellValue(material.getMaterialName());
        cell1.setCellStyle(leftAlignedStyle); // Apply left-aligned style

        Cell cell2 = row.createCell(START_COLUMN + 2);
        cell2.setCellValue(material.getSetWeight());
        cell2.setCellStyle(cellStyle);

        Cell cell3 = row.createCell(START_COLUMN + 3);
        cell3.setCellValue(material.getAchWeight());
        cell3.setCellStyle(cellStyle);

        Cell cell4 = row.createCell(START_COLUMN + 4);
        cell4.setCellValue(material.getAchWeight() - material.getSetWeight());
        cell4.setCellStyle(cellStyle);
    }

    private void createTotalRow(Sheet sheet, int rowNum, List<MaterialReport> materialReports, CellStyle cellStyle) {
        Row totalRow = sheet.createRow(rowNum);
        Cell cell1 = totalRow.createCell(START_COLUMN + 1);
        cell1.setCellValue("Total");
        cell1.setCellStyle(cellStyle);

        double totalSetWeight = materialReports.stream().mapToDouble(MaterialReport::getSetWeight).sum();
        double totalAchWeight = materialReports.stream().mapToDouble(MaterialReport::getAchWeight).sum();

        Cell cell2 = totalRow.createCell(START_COLUMN + 2);
        cell2.setCellValue(totalSetWeight);
        cell2.setCellStyle(cellStyle);

        Cell cell3 = totalRow.createCell(START_COLUMN + 3);
        cell3.setCellValue(totalAchWeight);
        cell3.setCellStyle(cellStyle);

        Cell cell4 = totalRow.createCell(START_COLUMN + 4);
        cell4.setCellValue(totalAchWeight - totalSetWeight);
        cell4.setCellStyle(cellStyle);
    }
    private void createBatchTotalRow(Sheet sheet, int rowNum, int totalNoOfBatches, int totalSetWeight, int totalAchWeight, CellStyle cellStyle) {
        Row totalRow = sheet.createRow(rowNum);
        Cell cell1 = totalRow.createCell(START_COLUMN + 1);
        cell1.setCellValue("Total");
        cell1.setCellStyle(cellStyle);
    
        Cell cell2 = totalRow.createCell(START_COLUMN + 2);
        cell2.setCellValue(totalNoOfBatches);
        cell2.setCellStyle(cellStyle);
    
        Cell cell3 = totalRow.createCell(START_COLUMN + 3);
        cell3.setCellValue(totalSetWeight);
        cell3.setCellStyle(cellStyle);
    
        Cell cell4 = totalRow.createCell(START_COLUMN + 4);
        cell4.setCellValue(totalAchWeight);
        cell4.setCellStyle(cellStyle);
    
        Cell cell5 = totalRow.createCell(START_COLUMN + 5);
        cell5.setCellValue(totalAchWeight - totalSetWeight);
        cell5.setCellStyle(cellStyle);
    }

    private String sanitizeSheetName(String name) {
        String sanitized = name.replaceAll("[\\/:?*\\[\\]]", "_");
        return sanitized.length() > 31 ? sanitized.substring(0, 31) : sanitized;
    }

    private String getUniqueSheetName(String baseName, Set<String> existingNames) {
        String name = baseName;
        int counter = 1;
        while (existingNames.contains(name)) {
            name = baseName + " (" + counter++ + ")";
        }
        existingNames.add(name);
        return name;
    }
}
