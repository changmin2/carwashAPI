package com.example.carwash.service.record;

import com.example.carwash.domain.dto.RecordDto;
import com.example.carwash.domain.record.CarWashRecord;
import com.example.carwash.repository.record.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public void registerRecord(String memberId, RecordDto recordDto) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = formatter.parse(recordDto.getDate());
        recordRepository.save(
                CarWashRecord.builder()
                        .place(recordDto.getPlace())
                        .date(date)
                        .imgUrl(recordDto.getImage())
                        .washList(recordDto.getWashList().toString())
                        .memberId(memberId)
                        .build()
        );
    }
}
