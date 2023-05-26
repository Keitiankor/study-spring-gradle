package com.example.studyspringgradle.domain.board.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.studyspringgradle.domain.board.domain.PostedPost;
import com.example.studyspringgradle.global.response.normal.Response;
import com.example.studyspringgradle.global.response.normal.ResponseCode;

import lombok.Getter;

@Getter
public class PostNewPostResponse {
    PostedPost postedPost;
    
    public PostNewPostResponse(PostedPost postedPost){
        this.postedPost = postedPost;
    }

    public ResponseEntity<?> response(){
        Response resp = Response.of(ResponseCode.RESPONSE_OK, this.postedPost);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
