package com.example.carwash.domain.member;

import jakarta.persistence.*;
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
    @GeneratedValue
    private Integer id;

    private String memberId;

    private String productName;

    private String category;

    private String cycle;

    private String imgUrl;
}
