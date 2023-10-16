package com.example.carwash.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Record {
    List<String> washList;
    String image;
    String washDate;
    String washPlace;


}
