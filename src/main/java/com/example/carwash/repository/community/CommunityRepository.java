package com.example.carwash.repository.community;

import com.example.carwash.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Integer> {
    @Query(value = "SELECT t.*\n" +
                   "  FROM carwash.Community t\n" +
                    "WHERE id>:community_id\n" +
                    "  AND category LIKE %:category%\n" +
                    "ORDER BY id\n" +
                    "LIMIT 20",nativeQuery = true)
    Optional<List<Community>> paginate(@Param(value = "community_id")int id,@Param(value = "category")String category);

    @Query(value = "SELECT t.*\n" +
                   "  FROM carwash.Community t\n" +
                    "WHERE category LIKE %:category%\n" +
                    "ORDER BY id DESC\n" +
                    "LIMIT 1",nativeQuery = true)
    Integer getFinalId(@Param(value = "category")String category);

    @Query(value = "SELECT * FROM carwash.Community\n" +
            "order by createDate desc\n" +
            "limit 5",nativeQuery = true)
    List<Community> getRecentCommunity();

    @Query(value = "SELECT * FROM carwash.Community\n" +
            "WHERE category ='자유게시판'\n" +
            "order by createDate desc\n" +
            "limit 5",nativeQuery = true)
    List<Community> getFreeCommunity();
}
