package com.example.carwash.controller.community;

import com.example.carwash.domain.community.Community;
import com.example.carwash.domain.dto.community.CommunityRequestDto;
import com.example.carwash.domain.member.Member;
import com.example.carwash.domain.record.CarWashRecord;
import com.example.carwash.service.community.CommunityService;
import com.example.carwash.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.carwash.utils.SecurityUtils.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final MemberService memberService;

    @PostMapping("/register")
    public void register(HttpServletRequest request,@RequestBody Map<String,String> json) throws ParseException {
        communityService.register(json);
    }

    @GetMapping("/communityPaginate")
    public Map<String, Object> paginate(@ModelAttribute CommunityRequestDto requestDto,HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);
        Map<String,Object> map = communityService.paginate(requestDto,member.getMemberId());
         return map;
    }

    @GetMapping("/recentCommunity")
    public List<Community> recentCommunity(HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);
        return communityService.recentCommunity(member.getMemberId());
    }

    @GetMapping("/recentFreeCommunity")
    public List<Community> recentFreeCommunity(){
        return communityService.recentFreeCommunity();
    }

    @GetMapping("/clickFavorite/{id}")
    void clickFavorite(@PathVariable("id")int id){
        communityService.clickFavorite(id);
    }

    @GetMapping("/downFavorite/{id}")
    void downFavorite(@PathVariable("id")int id){
        communityService.downFavorite(id);
    }

    @GetMapping("/delete/{id}")
    void deleteBoard(@PathVariable("id") int id){
        communityService.deleteBoard(id);
    }


}
