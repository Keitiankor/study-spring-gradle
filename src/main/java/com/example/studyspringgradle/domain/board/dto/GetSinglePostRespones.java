package com.example.studyspringgradle.domain.board.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.global.response.normal.Response;
import com.example.studyspringgradle.global.response.normal.ResponseCode;

import lombok.Getter;

@Getter
public class GetSinglePostRespones {
    BoardPost boardPost;

    public GetSinglePostRespones(BoardPost boardPost){
        this.boardPost = boardPost;
    }

    public ResponseEntity<?> response(){
        Response resp = Response.of(ResponseCode.RESPONSE_OK, this.boardPost);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
}
