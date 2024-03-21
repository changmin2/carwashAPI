package com.example.carwash.service.record;

import com.example.carwash.domain.record.MyRecord;
import com.example.carwash.repository.record.MyRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@Transactional
@RequiredArgsConstructor
public class MyRecordService {

    private final MyRecordRepository myRecordRepository;

    @Transactional
    public void registerMyRecord(String memberId, String washList)  {
        myRecordRepository.save(
                MyRecord.builder()
                        .memberId(memberId)
                        .washList(washList)
                        .build()
        );
    }
}
