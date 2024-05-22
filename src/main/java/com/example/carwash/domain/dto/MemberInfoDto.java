package com.example.carwash.domain.dto;

import com.example.carwash.domain.member.MyProduct;
import lombok.Data;

import java.util.List;

@Data
public class MemberInfoDto {

    private String record;
    private List<MyProduct> myProduct;
}
