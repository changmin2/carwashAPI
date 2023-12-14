package com.example.carwash.domain.dto.community;

import lombok.Data;

@Data
public class CommunityRequestDto {

    private int after;

    private int count;

    private String category;

    public CommunityRequestDto() {
        this.after = 0;
        this.count = 20;
        this.category = "";
    }
}
