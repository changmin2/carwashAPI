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

    @Query(value = "SELECT id,memberId,imgUrl,washList,place,date_add(date,INTERVAL 1 DAY) date\n" +
            "FROM carwash.CarWashRecord\n" +
            "where memberId = :memberId",nativeQuery = true)
    List<CarWashRecord> getRecord(String memberId);

    @Query(value = "SELECT * FROM carwash.CarWashRecord\n" +
            " WHERE memberId = :memberId\n" +
            " order by date desc\n" +
            " limit 3",nativeQuery = true)
    List<CarWashRecord> recentRecord(String memberId);
}
