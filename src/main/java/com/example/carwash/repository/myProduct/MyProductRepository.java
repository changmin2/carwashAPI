package com.example.carwash.repository.myProduct;

import com.example.carwash.domain.member.MyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyProductRepository extends JpaRepository<MyProduct,String> {
}
