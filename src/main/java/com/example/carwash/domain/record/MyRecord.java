package com.example.carwash.domain.record;

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
        name = "RECORD_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class MyRecord {


    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String memberId;

    private String washList;
}
