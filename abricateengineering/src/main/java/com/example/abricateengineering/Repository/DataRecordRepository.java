package com.example.abricateengineering.Repository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.abricateengineering.entity.DataRecord;
public interface DataRecordRepository extends JpaRepository<DataRecord, LocalDateTime>{
    
    List<DataRecord> findAllByDateTimeAfter(LocalDateTime dateTime);

    List<DataRecord> findAllByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query(value = "SELECT DateTime FROM dbo ORDER BY DateTime DESC LIMIT 1", nativeQuery = true)
    LocalDateTime findLatestRecord();
    
}



