package com.web.demo.repos.emp;

import com.web.demo.models.emp.HrRecords;
import com.web.demo.projection.HrDetails;
import com.web.demo.records.HrDetailsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface HrRecordRepository extends JpaRepository<HrRecords, Long> {

    // Returns the first 10 records
    List<HrRecords> findTop10By();

    List<HrDetails> findAllBy();

    @Query("SELECT new com.web.demo.records.HrDetailsRecord(hr.id, hr.city) FROM HrRecords hr")
    List<HrDetailsRecord> getHrDetailsObject();

    @Query("SELECT new com.web.demo.records.HrDetailsRecord(hr.id, hr.city) FROM HrRecords hr WHERE hr.id <= 1000")
    List<HrDetailsRecord> getHrDetailsObjectLimit1000();

    @Query("SELECT u FROM HrRecords u")
    Stream<HrRecords> streamAllUsers();
}
