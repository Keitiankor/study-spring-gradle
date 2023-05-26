package com.example.studyspringgradle.domain.account.dto;

import lombok.Getter;

@Getter
public class CheckAccountAvailable {
    boolean isAvailable;

    public CheckAccountAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
    
}
