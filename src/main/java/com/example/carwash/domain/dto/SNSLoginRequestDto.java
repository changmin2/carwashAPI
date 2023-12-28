package com.example.carwash.domain.dto;

import lombok.Data;

@Data
public class SNSLoginRequestDto {
    private String memberId;
    private String password;

    private String nickname;
}
