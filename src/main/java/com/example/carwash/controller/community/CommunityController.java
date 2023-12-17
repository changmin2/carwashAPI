package com.example.carwash.controller.community;

import com.example.carwash.domain.dto.community.CommunityRequestDto;
import com.example.carwash.service.community.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/register")
    public void register(HttpServletRequest request,@RequestBody Map<String,String> json) throws ParseException {
        communityService.register(json);
    }

    @GetMapping("/communityPaginate")
    public Map<String, Object> paginate(@ModelAttribute CommunityRequestDto requestDto){
        System.out.println(requestDto.toString());
        Map<String,Object> map = communityService.paginate(requestDto);
         return map;
    }
}
