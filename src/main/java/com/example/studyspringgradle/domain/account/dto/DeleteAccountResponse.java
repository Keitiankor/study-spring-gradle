package com.example.studyspringgradle.domain.account.dto;

import lombok.Getter;

@Getter
public class DeleteAccountResponse {
    int isSuccess;

    public DeleteAccountResponse(int isSuccess){
        this.isSuccess = isSuccess;
    }
    
}
