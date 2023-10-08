package com.example.carwash.controller.carwash;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/carwash")
public class CarWashController {

    @PostMapping("/register")
    public void register(HttpServletRequest request, @ModelAttribute Record record){
        System.out.println(record.toString());
    }
}
