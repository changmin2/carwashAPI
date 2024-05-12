package com.example.carwash.repository.myProduct;

import com.example.carwash.domain.member.MyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyProductRepository extends JpaRepository<MyProduct,String> {
    @Query(value = "SELECT * \n" +
            "FROM carwash.MyProduct\n" +
            "WHERE memberId =:memberId ",nativeQuery = true)
    List<MyProduct> getMyProduct(@Param(value = "memberId")String memberId);
}
