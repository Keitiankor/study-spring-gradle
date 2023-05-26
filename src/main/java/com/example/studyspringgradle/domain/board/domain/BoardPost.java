package com.example.studyspringgradle.domain.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BoardPost {
    private String title;
    private String post;
    private int postNumber;
    private Timestamp postDate;
    private Timestamp editedDate;
}
