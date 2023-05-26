package com.example.studyspringgradle.domain.board.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.studyspringgradle.global.response.normal.Response;
import com.example.studyspringgradle.global.response.normal.ResponseCode;

import lombok.Getter;

@Getter
public class PostNewCommentResponse {
    String comment;

    public PostNewCommentResponse(String comment){
        this.comment = comment;
    }
    public ResponseEntity<?> response(){
        Response resp = Response.of(ResponseCode.RESPONSE_OK, this.comment);
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }
}
