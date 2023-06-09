package com.example.studyspringgradle.domain.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

import com.example.studyspringgradle.global.Tuple;

@Getter
@Setter
public class BoardPost {
    private String title;
    private String post;
    private int postNumber;
    private Timestamp postDate;
    private Timestamp editedDate;
    private int commentCount;
    private List<Tuple<String, Timestamp>> comments;
    private int likes;
    private int dislikes;
}
