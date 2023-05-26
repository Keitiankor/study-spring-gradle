package com.example.studyspringgradle.domain.board.domain;



import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardPosts {
    private String title;
    private String post;
    private int postNumber;
    private Timestamp postDate;
    private Timestamp editedDate;
    private int commentCount;
    
}
