package com.example.studyspringgradle.domain.account.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.studyspringgradle.global.response.normal.Response;
import com.example.studyspringgradle.global.response.normal.ResponseCode;

import lombok.Getter;

@Getter
public class DeleteAccountResponse {
    int isSuccess;

    public DeleteAccountResponse(int isSuccess){
        this.isSuccess = isSuccess;
    }

    public ResponseEntity<?> response(){
        Response resp = Response.of(ResponseCode.DELETE_COMPLETE, Integer.toString(this.isSuccess));
        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
    }
    
}
