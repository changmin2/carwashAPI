package com.example.carwash.service.member;

import com.example.carwash.domain.dto.TokenInfo;
import com.example.carwash.domain.member.Member;
import com.example.carwash.provider.JwtTokenProvider;
import com.example.carwash.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    @Transactional
    public String join(String memberId,String password,String nickname,String intro){
        return memberRepository.save(
                    Member.builder()
                        .memberId(memberId)
                        .password(password)
                        .nickname(nickname)
                        .intro(intro)
                        .rcvAlarmYn("Y")
                        .roles(Collections.singletonList("USER"))
                        .build()
        ).getMemberId();
    }

    @Transactional
    public void edit(String memberId,String nickname,String intro){
        Member member = memberRepository.findByMemberId(memberId).get();
        member.setNickname(nickname);
        member.setIntro(intro);
    }

    public Optional<Member> getMember(String memberId){
        return memberRepository.findByMemberId(memberId);
    }

    @Transactional
    @Modifying
    public TokenInfo refresh(String refreshToken){
        if(jwtTokenProvider.validateToken(refreshToken)) {

            Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
            String memberId = authentication.getName();
            Member member = getMember(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));


            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

            return tokenInfo;
        }
        return null;
    }

    @Transactional
    public void withDrawl(Member member){
        memberRepository.delete(member);
    }

    public Member getMe(String accessToken){
        if(jwtTokenProvider.validateToken(accessToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

            String memberId = authentication.getName();
            Member member = getMember(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));

            return member;
        }

        return null;
    }

    public boolean duplicate(String email) {
        return memberRepository.findByMemberId(email).isEmpty();
    }

    public boolean duplicateNickname(String nickName){
        return memberRepository.duplicateNickName(nickName).isEmpty();
    }

    public Member findByNickName(String nickname){
        return memberRepository.duplicateNickName(nickname).get();
    }

    @Modifying
    public void setFirebaseToken(Member member, String firebaseToken) {
        member.setFirebaseToken(firebaseToken);
    }

    @Modifying
    public void updateAlarmYn(Member member) {
        member.setRcvAlarmYn(member.getRcvAlarmYn().equals("Y") ? "N" : "Y");
    }

    public List<String> getRcvAlramY() {
        return memberRepository.getRcvAlramY();
    }

    public String getRcvAlramYMember(String nickname){
        return memberRepository.getRcvAlramYMember(nickname);
    }
}
