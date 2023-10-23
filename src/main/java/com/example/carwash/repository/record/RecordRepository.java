package com.example.carwash.repository.record;

import com.example.carwash.domain.member.Member;
import com.example.carwash.domain.record.CarWashRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<CarWashRecord, Integer> {
}
