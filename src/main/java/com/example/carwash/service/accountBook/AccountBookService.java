package com.example.carwash.service.accountBook;

import com.example.carwash.domain.dto.AccountBook;
import com.example.carwash.repository.accountBook.AccountBookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;

    @Transactional
    public AccountBook add(String memberId, AccountBook accountBookDto){
        return accountBookRepository.save(
                AccountBook.builder()
                        .memberId(memberId)
                        .title(accountBookDto.getTitle())
                        .date(accountBookDto.getDate())
                        .category(accountBookDto.getCategory())
                        .memo(accountBookDto.getMemo())
                        .cost(accountBookDto.getCost())
                        .build()
        );
    }

    public List<AccountBook> getAccountBooks(String memberId) {
        return accountBookRepository.getAccountBooks(memberId);
    }

    @Transactional
    public void deleteAccountBooks(int id) {
        accountBookRepository.deleteById(id);
    }
}
