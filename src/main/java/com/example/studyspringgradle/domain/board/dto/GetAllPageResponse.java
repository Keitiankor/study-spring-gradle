package com.example.studyspringgradle.domain.board.dto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.global.response.normal.Response;
import com.example.studyspringgradle.global.response.normal.ResponseCode;

import lombok.Getter;

@Getter
public class GetAllPageResponse {
    List<BoardPost> postList;

    public GetAllPageResponse(List<BoardPost> postList) {
        this.postList = postList;
    }

    public ResponseEntity<?> response() {
        Response resp = Response.of(ResponseCode.RESPONSE_OK, this.postList);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
