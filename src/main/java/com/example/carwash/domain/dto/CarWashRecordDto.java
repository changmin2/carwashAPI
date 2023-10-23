package com.example.carwash.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CarWashRecordDto {

    private String memberId;

    private String imgUrl;

    private String washList;

    private String place;

    private Date date;
}
