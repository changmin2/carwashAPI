package com.example.carwash.domain.community;

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
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMUNITY_SEQ_GENERATOR")
    private Integer id;

    private String creator;

    private String content;
    private Date createDate;

    private Integer hits;

    private Integer favorite;

    private String category;

    private String title;



}
