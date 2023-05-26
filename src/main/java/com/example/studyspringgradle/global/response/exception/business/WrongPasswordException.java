package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class WrongPasswordException extends BusinessException {

    public WrongPasswordException(){
        super(CodeError.WRONG_PASSWORD);
    }
    
}
