package com.example.carwash.domain.dto.community;

import lombok.Data;

@Data
public class CommentRequestDto {

    private int after;
    private int count;

    public CommentRequestDto() {
        this.after = 0;
        this.count = 20;
    }
}
