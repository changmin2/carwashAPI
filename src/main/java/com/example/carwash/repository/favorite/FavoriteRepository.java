package com.example.carwash.repository.favorite;

import com.example.carwash.domain.community.Community;
import com.example.carwash.domain.member.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,Integer> {
    @Query(value = "SELECT board_id\n" +
            "  FROM carwash.Favorite\n" +
            " WHERE memberId =:memberId ",nativeQuery = true)
    List<Integer> getFavorites(@Param(value = "memberId")String memberId);

    @Query(value = "DELETE FROM carwash.Favorite\n" +
            " WHERE memberId=:memberId\n" +
            "   AND boardId=:boardId",nativeQuery = true)
    void deleteHeart(int boardId,String memberId);
}
