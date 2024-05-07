package com.example.carwash.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "MYPRODUCT_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class MyProduct {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String memberId;

    private String productName;

    private String category;

    private String cycle;

    private String imgUrl;
}
