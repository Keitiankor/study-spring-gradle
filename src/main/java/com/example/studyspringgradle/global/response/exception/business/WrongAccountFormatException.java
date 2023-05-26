package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class WrongAccountFormatException extends BusinessException {

    public WrongAccountFormatException(){
        super(CodeError.WRONG_ACCOUNT_FORMAT);
    }
    
}
