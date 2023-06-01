package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class WrongPasswordFormatException extends BusinessException {

    public WrongPasswordFormatException() {
        super(CodeError.WRONG_PASSWORD_FORMAT);
    }

}
