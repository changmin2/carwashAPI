package com.example.carwash.domain.comment;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long comment_id;

    private int id;
    private String creator;

    private String content;

    private Date createDate;

    //커뮤니티 아이디
    private int commentList_id;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(referencedColumnName = "comment_id")
    private List<ReComment> commentList = new ArrayList<>();

}
