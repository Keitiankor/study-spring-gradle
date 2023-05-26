package com.example.studyspringgradle.domain.account.dto;

import lombok.Getter;

@Getter
public class ChangePasswordResponse {
    int isSuccess;

    public ChangePasswordResponse(int isSuccess){
        this.isSuccess = isSuccess;
    }
    
}
