package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class BadRequestException extends BusinessException {
    
    public BadRequestException(){
        super(CodeError.BAD_REQUEST);
    }
    
}
