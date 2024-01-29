package com.example.carwash.controller.community;

import com.example.carwash.domain.member.Favorite;
import com.example.carwash.domain.member.Member;
import com.example.carwash.service.favorite.FavoriteService;
import com.example.carwash.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.carwash.utils.SecurityUtils.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    final private MemberService memberService;
    final private FavoriteService favoriteService;

    //즐겨찾기 정보 다 가져오기
    @GetMapping("/")
    public List<Integer> getFavorites(HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);

        return favoriteService.getFavorites(member.getMemberId());
    }

    @GetMapping("/add/{id}")
    public void AddClip(@PathVariable("id") int board_id, HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);
        favoriteService.addHeart(board_id,member.getMemberId());
    }

    @GetMapping("/deleteClip/{id}")
    public void DeleteClip(@PathVariable("id") int board_id,HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);
        favoriteService.deleteClip(board_id, member.getMemberId());
    }
}
