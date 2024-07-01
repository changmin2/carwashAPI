package com.example.carwash.controller.accountBook;

import com.example.carwash.domain.dto.AccountBook;
import com.example.carwash.domain.member.Member;
import com.example.carwash.service.accountBook.AccountBookService;
import com.example.carwash.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.example.carwash.utils.SecurityUtils.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accountBook")
public class AccountBookController {

    private final MemberService memberService;

    private final AccountBookService accountBookService;

    @PostMapping("/add")
    public AccountBook add(HttpServletRequest request, @RequestBody AccountBook accountBookDto) throws ParseException {

        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);

        return accountBookService.add(member.getMemberId(),accountBookDto);
    }

    @GetMapping("/getAccountBooks")
    public List<AccountBook> getAccountBooks(HttpServletRequest request) throws ParseException {

        String authroizationHeader = request.getHeader(AUTHORIZATION);
        if(authroizationHeader == null || !authroizationHeader.startsWith(TOKEN_HEADER_PREFIX)){
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        String accessToken = authroizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Member member= memberService.getMe(accessToken);

        return accountBookService.getAccountBooks(member.getMemberId());
    }

    @GetMapping("/deleteAccountBooks/{id}")
    public void deleteAccountBooks(@PathVariable("id") int id) throws ParseException {
        accountBookService.deleteAccountBooks(id);
    }
}
