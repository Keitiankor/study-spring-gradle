package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class AccountConflictException extends BusinessException {

    public AccountConflictException() {
        super(CodeError.CONFLICT);
    }

}
