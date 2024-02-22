package com.example.carwash.repository.block;

import com.example.carwash.domain.member.Block;
import com.example.carwash.domain.member.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block,Integer> {

    @Query(value = "SELECT blockId\n" +
            "  FROM carwash.Block\n" +
            " WHERE memberId =:memberId ",nativeQuery = true)
    List<String> getBlocks(@Param(value = "memberId")String memberId);
}
