package com.example.carwash.controller;


import com.example.carwash.domain.dto.*;
import com.example.carwash.domain.member.Member;
import com.example.carwash.domain.member.MyProduct;
import com.example.carwash.service.member.MemberService;
import com.example.carwash.service.myProduct.MyProductService;
import com.example.carwash.service.record.MyRecordService;
import com.example.carwash.service.record.RecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.example.carwash.utils.SecurityUtils.TOKEN_HEADER_PREFIX;
import static java.util.Map.entry;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MyProductService myProductService;

    private final MyRecordService myRecordService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        Member member = memberService.getMember(memberLoginRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));
        if(!passwordEncoder.matches(memberLoginRequestDto.getPassword(),member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        TokenInfo tokenInfo = memberService.login(memberLoginRequestDto.getMemberId(), member.getPassword());
        System.out.println(member.toString());
        return tokenInfo;
    }

    @PostMapping("/snslogin")
    public TokenInfo snslogin(@RequestBody SNSLoginRequestDto memberLoginRequestDto) {
        if(memberService.getMember(memberLoginRequestDto.getMemberId()).isEmpty()){
            memberService.join(memberLoginRequestDto.getMemberId(),memberLoginRequestDto.getPassword(),memberLoginRequestDto.getNickname(),"");
        }
        Member member = memberService.getMember(memberLoginRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("로그인 오류 입니다."));

        TokenInfo tokenInfo = memberService.login(memberLoginRequestDto.getMemberId(), member.getPassword());

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
        json.put("nickname",member.getNickname());
        json.put("intro",member.getIntro());
        json.put("firebaseToken", member.getFirebaseToken());
        json.put("rcvAlarmYn",member.getRcvAlarmYn());
        return json;

    }

    @PostMapping("/join")
    public String join(@RequestBody Map<String,String> user){
        String memberId = user.get("memberId");
        //아이디가 중복 안됐을떄
        if(memberService.duplicate(user.get("memberId")) == false){
            return "-1";
        }else if(memberService.duplicateNickname(user.get("nickname")) == false){
            return "-2";
        }else{
            String password = passwordEncoder.encode(user.get("password"));
            String nickname = user.get("nickname");
            String intro = user.get("intro");
            return memberService.join(memberId,password,nickname,intro);
        }
    }

    @PostMapping("/edit")
    public String edit(HttpServletRequest request, @RequestBody Map<String,String> user){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String,String> json = new HashMap<>();
        Member member= memberService.getMe(accessToken);
        System.out.println(user.toString());
        //닉네임이 중복 됐을떄
        if(memberService.duplicateNickname(user.get("nickname")) == false && member.getNickname() != user.get("nickname")){
            return "-2";
        }else{
            String nickname = user.get("nickname");
            String intro = user.get("intro");
            memberService.edit(member.getMemberId(),nickname,intro);
            return "sucess";
        }
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


    // 회원탈퇴
    @GetMapping("/withDrawal")
    public void WithDrawl(HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);
        memberService.withDrawl(member);
    }

    //나의 세차용품 등록
    @PostMapping("/registerMyProduct")
    public MyProduct  getMyRecord(HttpServletRequest request, @RequestBody MyProduct myProduct) {

        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);
        myProduct.setMemberId(member.getMemberId());

        return myProductService.registerMyProduct(myProduct);
    }

    @GetMapping("/getMyProduct")
    public List<MyProduct> getMyProduct(HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);

        return myProductService.getMyProduct(member.getMemberId());
    }

    @GetMapping("/getUserInfo/{nickname}")
    public MemberInfoDto getUserInfo(@PathVariable("nickname")String nickname){
        try{
            Member member = memberService.findByNickName(nickname);

            MemberInfoDto memberInfoDto = new MemberInfoDto();
            memberInfoDto.setRecord(
                    myRecordService.getMyRecord(member.getMemberId())
            );
            memberInfoDto.setMyProduct(
                    myProductService.getMyProduct(member.getMemberId())
            );
            System.out.println(memberInfoDto.toString());
            return memberInfoDto;
        }catch (Exception e){
            MemberInfoDto memberInfoDto = new MemberInfoDto();
            memberInfoDto.setRecord("");
            List<MyProduct> list = Collections.emptyList();
            memberInfoDto.setMyProduct(list);
            System.out.println(memberInfoDto.toString());
            return memberInfoDto;

        }


    }

    //파이어베이스 기기 토큰 등록
    @PostMapping("/setFirebaseToken")
    public void setFirebaseToken(HttpServletRequest request, @RequestBody FirebaseTokenDto tokenDto) {

        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);
        memberService.setFirebaseToken(member,tokenDto.getFirebaseToken());
    }

    //푸쉬 알림 수신 설정
    @PostMapping("/updateAlarmYn")
    public void updateAlarmYn(HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String,String> json = new HashMap<>();
        Member member= memberService.getMe(accessToken);

        memberService.updateAlarmYn(member);

    }
}
