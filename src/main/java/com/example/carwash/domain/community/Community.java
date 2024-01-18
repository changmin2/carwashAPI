package com.example.carwash.domain.community;

import com.example.carwash.domain.comment.Comment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMUNITY_SEQ_GENERATOR")
    private Integer id;

    private String creator;

    private String content;
    private Date createDate;

    private Integer hits;

    private Integer favorite;

    private String category;

    private String title;

    private String hastag;

    private String imgUrls;

    private Integer commentCnt;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(referencedColumnName = "id")
    private List<Comment> commentList = new ArrayList<>();



}
