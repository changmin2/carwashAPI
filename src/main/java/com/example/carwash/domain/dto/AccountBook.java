package com.example.carwash.domain.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOOUNTBOOK_SEQ_GENERATOR")
    private Integer id;

    private String memberId;
    private Date date;
    private String category;
    private String title;
    private int cost;
    private String memo;
}
