package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class IsNullDataException extends BusinessException {
    public IsNullDataException(){
        super(CodeError.BAD_REQUEST);
    }
    
}
