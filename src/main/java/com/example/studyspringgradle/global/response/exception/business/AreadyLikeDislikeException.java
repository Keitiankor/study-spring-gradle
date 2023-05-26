package com.example.studyspringgradle.global.response.exception.business;

import com.example.studyspringgradle.global.response.exception.response.CodeError;

public class AreadyLikeDislikeException extends BusinessException {
    public AreadyLikeDislikeException(){
        super(CodeError.AREADY_LDL);
    }
    
}
