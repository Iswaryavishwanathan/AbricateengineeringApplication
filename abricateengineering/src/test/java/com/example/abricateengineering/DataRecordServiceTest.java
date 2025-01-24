package com.example.abricateengineering;

import com.example.abricateengineering.DAO.FormattedDataDAO;
import com.example.abricateengineering.DAO.RowRecordDAO;
import com.example.abricateengineering.Repository.DataRecordRepository;
import com.example.abricateengineering.Service.DataDAOService;
import com.example.abricateengineering.Service.DataRecordService;
import com.example.abricateengineering.Service.MaterialReportService;
import com.example.abricateengineering.entity.DataRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class DataRecordServiceTest {

    @Mock
    private DataRecordRepository dataRecordRepository;

    @Mock
    private DataDAOService dataDAOService;

    @Mock
    private MaterialReportService materialReportService;

    private DataRecordService dataRecordService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);  // Make sure this is initialized before using mocks
        dataRecordService = new DataRecordService(dataRecordRepository, dataDAOService, materialReportService);
    }

    @Test
public void testGetFormattedData() {
    // Arrange
    String startDateStr = "2025-01-01";
    String endDateStr = "2025-01-20";

    // Mock data
    LocalDateTime startDateTime = LocalDate.parse(startDateStr).atStartOfDay();
    LocalDateTime endDateTime = LocalDate.parse(endDateStr).atStartOfDay();

    DataRecord mockDataRecord = mock(DataRecord.class);
    when(mockDataRecord.getDateTime()).thenReturn(startDateTime);
    when(mockDataRecord.getFormula()).thenReturn("Test Formula");

    // Mock T values
    when(mockDataRecord.getTValue(anyInt())).thenReturn((float) 100);

    // // Mock material names (N values)
    when(mockDataRecord.getNValue(1)).thenReturn("Material A"); // Mock for n01
    when(mockDataRecord.getNValue(2)).thenReturn("Material B"); // Mock for n02

    // Mock achieved weights (A values)
    when(mockDataRecord.getAValue(anyInt())).thenReturn((float) 90);

    List<DataRecord> dataRecordList = new ArrayList<>();
    dataRecordList.add(mockDataRecord);

    // Mock repository method
    when(dataRecordRepository.findAllByDateTimeBetween(startDateTime, endDateTime)).thenReturn(dataRecordList);

    // Act
    FormattedDataDAO formattedData = dataRecordService.getFormattedData(startDateStr, endDateStr);

    // Assert
    assertNotNull(formattedData, "Formatted data should not be null");
    assertEquals(1, formattedData.getColumnNames().size(), "There should be 1 unique column name");
    assertEquals(2, formattedData.getRecords().size(), "There should be 2 records (setWeight and achWeight)");

    RowRecordDAO setWeightRecord = formattedData.getRecords().get(0);
    assertEquals("setWeight", setWeightRecord.getRowType(), "First record should be of type setWeight");

    RowRecordDAO achWeightRecord = formattedData.getRecords().get(1);
    assertEquals("achWeight", achWeightRecord.getRowType(), "Second record should be of type achWeight");

    // Verify interactions with repository
    verify(dataRecordRepository, times(1)).findAllByDateTimeBetween(startDateTime, endDateTime);
}
}
