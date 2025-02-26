package com.example.abricateengineering.Service;
import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.Repository.DataRecordRepository;
import com.example.abricateengineering.entity.DataRecord;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Service
public class DataReceiveService {

     @Autowired
    private DataRecordRepository dataRecordRepository;
    
    @Transactional
    public void processReceivedData(List<DataRecordDAO> dataRecordDAOs) {
         List<DataRecord> dataRecordsToSave = new ArrayList<>();
     
        for (DataRecordDAO record : dataRecordDAOs) {
            System.out.println("Processing record: " + record.getDateTime());

            DataRecord dataRecord = new DataRecord();
            dataRecord.setDateTime(record.getDateTime());
            dataRecord.setFormula(record.getFormula());
     
    Float[] tValues = record.getTValues();

    if (tValues.length >= 20) {
        dataRecord.setT01(tValues[0]);
        dataRecord.setT02(tValues[1]);
        dataRecord.setT03(tValues[2]);
        dataRecord.setT04(tValues[3]);
        dataRecord.setT05(tValues[4]);
        dataRecord.setT06(tValues[5]);
        dataRecord.setT07(tValues[6]);
        dataRecord.setT08(tValues[7]);
        dataRecord.setT09(tValues[8]);
        dataRecord.setT10(tValues[9]);
        dataRecord.setT11(tValues[10]);
        dataRecord.setT12(tValues[11]);
        dataRecord.setT13(tValues[12]);
        dataRecord.setT14(tValues[13]);
        dataRecord.setT15(tValues[14]);
        dataRecord.setT16(tValues[15]);
        dataRecord.setT17(tValues[16]);
        dataRecord.setT18(tValues[17]);
        dataRecord.setT19(tValues[18]);
        dataRecord.setT20(tValues[19]);
    }
    Float[] aValues = record.getAValues();
    if (aValues.length >= 20) {
        dataRecord.setA01(aValues[0]);
        dataRecord.setA02(aValues[1]);
        dataRecord.setA03(aValues[2]);
        dataRecord.setA04(aValues[3]);
        dataRecord.setA05(aValues[4]);
        dataRecord.setA06(aValues[5]);
        dataRecord.setA07(aValues[6]);
        dataRecord.setA08(aValues[7]);
        dataRecord.setA09(aValues[8]);
        dataRecord.setA10(aValues[9]);
        dataRecord.setA11(aValues[10]);
        dataRecord.setA12(aValues[11]);
        dataRecord.setA13(aValues[12]);
        dataRecord.setA14(aValues[13]);
        dataRecord.setA15(aValues[14]);
        dataRecord.setA16(aValues[15]);
        dataRecord.setA17(aValues[16]);
        dataRecord.setA18(aValues[17]);
        dataRecord.setA19(aValues[18]);
        dataRecord.setA20(aValues[19]);
    }
    String[] nValues = record.getNValues();
    if (nValues.length >= 20) {
        dataRecord.setN01(nValues[0]);
        dataRecord.setN02(nValues[1]);
        dataRecord.setN03(nValues[2]);
        dataRecord.setN04(nValues[3]);
        dataRecord.setN05(nValues[4]);
        dataRecord.setN06(nValues[5]);
        dataRecord.setN07(nValues[6]);
        dataRecord.setN08(nValues[7]);
        dataRecord.setN09(nValues[8]);
        dataRecord.setN10(nValues[9]);
        dataRecord.setN11(nValues[10]);
        dataRecord.setN12(nValues[11]);
        dataRecord.setN13(nValues[12]);
        dataRecord.setN14(nValues[13]);
        dataRecord.setN15(nValues[14]);
        dataRecord.setN16(nValues[15]);
        dataRecord.setN17(nValues[16]);
        dataRecord.setN18(nValues[17]);
        dataRecord.setN19(nValues[18]);
        dataRecord.setN20(nValues[19]);
    }
          
                           dataRecordsToSave.add(dataRecord);
                System.out.println("Prepared record: " + dataRecord);

        }
    dataRecordRepository.saveAll(dataRecordsToSave);
    System.out.println("All records saved successfully.");
    }
    @Transactional 
    @Scheduled(cron = "0 0 0 * * ?") 
    public void deleteOldRecords() {
        LocalDateTime cutoffDate = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
        int deletedCount = dataRecordRepository.deleteByDateTimeBefore(cutoffDate);
        System.out.println(deletedCount + " records deleted successfully.");
    }
    
}






