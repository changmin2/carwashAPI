package com.example.carwash.controller.community;

import com.example.carwash.domain.member.Member;
import com.example.carwash.service.community.BlockService;
import com.example.carwash.service.favorite.FavoriteService;
import com.example.carwash.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.carwash.utils.SecurityUtils.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockController {

    final private MemberService memberService;
    final private BlockService blockService;

    //차단 유저 정보 다 가져오기
    @GetMapping("/getBlock")
    public List<String> getBlocks(HttpServletRequest request){
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);

        return blockService.getBlocks(member.getMemberId());
    }

    @GetMapping("/addBlock/{blockId}")
    public void addBlock(@PathVariable("blockId") String blockId, HttpServletRequest request){
        System.out.println(blockId);
        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }

        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);
        blockService.addBlock(member.getMemberId(),blockId);
    }
}
