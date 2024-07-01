package com.example.carwash.repository.accountBook;

import com.example.carwash.domain.dto.AccountBook;
import com.example.carwash.domain.member.MyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook,Integer> {

    @Query(value = "SELECT * \n" +
            "FROM carwash.AccountBook\n" +
            "WHERE memberId =:memberId ",nativeQuery = true)
    List<AccountBook> getAccountBooks(@Param(value = "memberId")String memberId);
}
