package com.example.carwash.controller.carwash;

import com.example.carwash.domain.dto.RecordDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarWashController {

    @PostMapping("/register")
    public void register(HttpServletRequest request,@RequestBody RecordDto recordDto){
        System.out.println(recordDto.getDate());
        System.out.println(recordDto.getWashList());
        System.out.println(recordDto.getPlace());
    }
}
