package com.example.abricateengineering.Service;
import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.Repository.DataRecordRepository;
import com.example.abricateengineering.entity.DataRecord;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            dataRecord.setTValues(record.getTValues());
            dataRecord.setAValues(record.getAValues());
            dataRecord.setNValues(record.getNValues());
           
            // dataRecordRepository.save(dataRecord);
            // System.out.println("Saved record: " + dataRecord);
                dataRecordsToSave.add(dataRecord);
                System.out.println("Prepared record: " + dataRecord);

        }
    dataRecordRepository.saveAll(dataRecordsToSave);
    System.out.println("All records saved successfully.");
    }
}


