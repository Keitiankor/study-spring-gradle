package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class WrongPasswordFormatExvception extends BusinessException {

    public WrongPasswordFormatExvception(){
        super(CodeError.WRONG_PASSWORD_FORMAT);
    }
    
}
