package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final CodeError codeError;

    public BusinessException(CodeError codeError){
        this.codeError = codeError;
    }

    public CodeError getErrorCode(){
        return codeError;
    }
    
}
