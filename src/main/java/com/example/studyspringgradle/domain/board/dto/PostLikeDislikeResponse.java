package com.example.studyspringgradle.domain.board.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.studyspringgradle.global.response.normal.Response;
import com.example.studyspringgradle.global.response.normal.ResponseCode;

import lombok.Getter;

@Getter
public class PostLikeDislikeResponse {
    boolean isSuccess;
    public PostLikeDislikeResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ResponseEntity<?> response(){
        Response resp = Response.of(ResponseCode.RESPONSE_OK, this.isSuccess);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
