package com.example.carwash.repository.record;

import com.example.carwash.domain.record.MyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRecordRepository extends JpaRepository<MyRecord,String> {

}
