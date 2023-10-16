package com.example.carwash.controller;


import com.example.carwash.domain.dto.MemberLoginRequestDto;
import com.example.carwash.domain.dto.TokenInfo;
import com.example.carwash.domain.member.Member;
import com.example.carwash.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.carwash.utils.SecurityUtils.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        System.out.println(memberLoginRequestDto.toString());
        Member member = memberService.getMember(memberLoginRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));
        if(!passwordEncoder.matches(memberLoginRequestDto.getPassword(),member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        TokenInfo tokenInfo = memberService.login(memberLoginRequestDto.getMemberId(), member.getPassword());
        System.out.println(member.toString());
        return tokenInfo;
    }

    //아이디 중복 체크
    @PostMapping("/duplicate")
    public boolean duplicate(@RequestBody Map<String,String> map){
        return memberService.duplicate(map.get("email"));
    }

    @GetMapping("/me")
    public Map<String,String> getMe(HttpServletRequest request){

        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String,String> json = new HashMap<>();
        Member member= memberService.getMe(accessToken);
        System.out.println("me: "+member.toString());
        json.put("username",member.getMemberId());
        json.put("password",member.getPassword());
        return json;

    }

    @PostMapping("/join")
    public String join(@RequestBody Map<String,String> user){
        String memberId = user.get("memberId");
        String password = passwordEncoder.encode(user.get("password"));
        return memberService.join(memberId,password);
    }

    @PostMapping("/token")
    public TokenInfo refresh(HttpServletRequest request, HttpServletResponse response){
        System.out.println("리프레시 토큰 발급");
        String authroizationHeader = request.getHeader(AUTHORIZATION);

        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        String refreshToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        return memberService.refresh(refreshToken);
    }



}
