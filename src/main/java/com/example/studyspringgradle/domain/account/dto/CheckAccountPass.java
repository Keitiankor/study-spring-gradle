package com.example.studyspringgradle.domain.account.dto;

import lombok.Getter;

@Getter
public class CheckAccountPass {
    boolean isRight;

    public CheckAccountPass(boolean isRight){
        this.isRight = isRight;
    }
    
}
