package com.example.carwash.controller.carwash;

import com.example.carwash.domain.dto.CarWashRecordDto;
import com.example.carwash.domain.dto.RecordDto;
import com.example.carwash.domain.member.Member;
import com.example.carwash.domain.record.CarWashRecord;
import com.example.carwash.service.member.MemberService;
import com.example.carwash.service.record.MyRecordService;
import com.example.carwash.service.record.RecordService;
import com.example.carwash.service.s3.S3Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.carwash.utils.SecurityUtils.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarWashController {

    private final MemberService memberService;
    private final RecordService recordService;
    private final MyRecordService myRecordService;


    @PostMapping("/register")
    public CarWashRecord register(HttpServletRequest request,@RequestBody RecordDto recordDto) throws ParseException {

        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String,String> json = new HashMap<>();
        Member member= memberService.getMe(accessToken);
        return recordService.registerRecord(member.getMemberId(),recordDto);

    }

    @GetMapping("/{date}")
    public List<CarWashRecord> getRecord(HttpServletRequest request,@PathVariable("date")String date) throws ParseException {
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String,String> json = new HashMap<>();
        Member member= memberService.getMe(accessToken);
        return recordService.getRecord(member.getMemberId(), date);

    }

    @PostMapping("/recentRecord")
    public List<CarWashRecord> recentRecord(@RequestBody Map<String,String> json){
        return recordService.recnetRecord(json.get("memberId"));
    }

    @GetMapping("/myrecord/register/{washList}")
    public void registerMyRecord(HttpServletRequest request,@PathVariable("washList")String washList) {

        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);

        myRecordService.registerMyRecord(member.getMemberId(),washList);


    }

}
