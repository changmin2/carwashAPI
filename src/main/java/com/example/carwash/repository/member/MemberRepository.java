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

    @Query(value = "SELECT distinct firebaseToken FROM carwash.Member\n" +
            "where firebaseToken is not null\n" +
            "  and rcvAlarmYn = 'Y'",nativeQuery = true)
    List<String> getRcvAlramY();

    @Query(value = "SELECT distinct firebaseToken FROM carwash.Member\n" +
            "where firebaseToken is not null\n" +
            "  and rcvAlarmYn = 'Y'" +
            "  and nickname =:nickName",nativeQuery = true)
    String getRcvAlramYMember(@Param("nickName") String nickName);

}
