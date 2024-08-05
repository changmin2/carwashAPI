package com.example.carwash.domain.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessageDto {
    private String title;
    private String body;
    private String image;
}
