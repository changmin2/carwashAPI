package com.example.carwash.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RecordDto {
    private List<String> washList;
    private String image;
    private String date;
    private String place;

    private MultipartFile multipartFile;

}
