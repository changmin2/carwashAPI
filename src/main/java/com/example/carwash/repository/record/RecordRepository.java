package com.example.carwash.repository.record;

import com.example.carwash.domain.dto.CarWashRecordDto;
import com.example.carwash.domain.member.Member;
import com.example.carwash.domain.record.CarWashRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<CarWashRecord, Integer> {

    @Query(value = "SELECT * FROM carwash.CarWashRecord\n" +
            "where date between :date and DATE_ADD(:date,INTERVAL 1 MONTH)\n" +
            "and memberId = :memberId",nativeQuery = true)
    List<CarWashRecord> getRecord(String memberId, String date);

}
