package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class WrongPostIdException extends BusinessException {

    public WrongPostIdException(){
        super(CodeError.BAD_REQUEST);
    }
    
}
