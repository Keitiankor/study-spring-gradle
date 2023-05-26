package com.example.studyspringgradle.domain.account.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.studyspringgradle.global.response.normal.Response;
import com.example.studyspringgradle.global.response.normal.ResponseCode;

import lombok.Getter;

@Getter
public class PostNewAccountResponse {
    String accountInfo;

    public PostNewAccountResponse(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public ResponseEntity<?> reponse() {
        Response resp = Response.of(ResponseCode.CREATE_COMPLETE, this.accountInfo);
        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
    }

}
