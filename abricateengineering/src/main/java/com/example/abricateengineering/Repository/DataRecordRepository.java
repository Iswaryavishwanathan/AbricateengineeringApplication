package com.example.abricateengineering.Repository;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.abricateengineering.entity.DataRecord;


public interface DataRecordRepository extends JpaRepository<DataRecord, LocalDateTime>{
    List<DataRecord> findAllByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    // // Fetch the most recent record for a material name before a given date
    // Optional<DataRecord> findFirstByMaterialNameAndDateTimeBeforeOrderByDateTimeDesc(String materialName, LocalDateTime dateTime);
}



