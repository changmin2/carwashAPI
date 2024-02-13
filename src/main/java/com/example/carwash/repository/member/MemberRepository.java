package com.example.carwash.repository.member;


import com.example.carwash.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);

    @Query(value = "SELECT * \n" +
            "  FROM carwash.Member\n" +
            " WHERE nickname =:nickName",nativeQuery = true)
    Optional<Member> duplicateNickName(@Param("nickName") String nickName);

}
