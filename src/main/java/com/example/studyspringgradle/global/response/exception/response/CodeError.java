package com.example.studyspringgradle.global.response.exception.response;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CodeError {

    INVALID_INPUT_VALUE(400, "C001"),

    BAD_REQUEST(400, "E000"),
    CONFLICT(409, "E001"), // 아이디 중복
    WRONG_ACCOUNT_FORMAT(400, "E002"), // 아이디 형식 불일치
    WRONG_PASSWORD_FORMAT(400,"E003"), // 비밀번호 형식 불일치
    WRONG_PASSWORD(400, "E004"), // 비밀번호 불일치

    AREADY_LDL(400, "E010"),
    ;

    private final String code;
    private final int status;

    CodeError(int status, String code) {
        this.status = status;
        this.code = code;
    }

    public int getStatus() {
        return this.status;
    }

    public String getCode() {
        return this.code;
    }

}
