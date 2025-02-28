package com.example.abricateengineering.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.abricateengineering.Repository.DataRecordRepository;
@Service
public class SendLastDateService {
     private final DataRecordRepository dataRecordRepository;

    public SendLastDateService(DataRecordRepository dataRecordRepository) {
        this.dataRecordRepository = dataRecordRepository;
    }

    public LocalDateTime getLastRecord() {
        return dataRecordRepository.findLatestRecord();
    }

}
