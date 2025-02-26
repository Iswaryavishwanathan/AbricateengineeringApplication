package com.example.abricateengineering.Repository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.abricateengineering.entity.DataRecord;
public interface DataRecordRepository extends JpaRepository<DataRecord, LocalDateTime>{
    
    List<DataRecord> findAllByDateTimeAfter(LocalDateTime dateTime);

    List<DataRecord> findAllByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
       @Query("SELECT d FROM DataRecord d WHERE d.dateTime BETWEEN :start AND :end ORDER BY d.dateTime ASC")
    List<DataRecord> findRecordsSorted(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

        @Query(value = "SELECT DateTime FROM dbo ORDER BY DateTime DESC LIMIT 1", nativeQuery = true)
    LocalDateTime findLatestRecord();

    @Modifying
        @Query("DELETE FROM DataRecord d WHERE d.dateTime < :cutoffDate")
    int deleteByDateTimeBefore(@Param("cutoffDate") LocalDateTime cutoffDate);

}



