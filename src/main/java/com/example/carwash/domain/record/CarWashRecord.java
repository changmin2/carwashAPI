package com.example.carwash.domain.record;

import jakarta.persistence.*;
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
@SequenceGenerator(
        name = "RECORD_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class CarWashRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECORD_SEQ_GENERATOR")
    private Integer id;

    private String memberId;

    private String imgUrl;

    private String washList;

    private String place;

    private Date date;
}
