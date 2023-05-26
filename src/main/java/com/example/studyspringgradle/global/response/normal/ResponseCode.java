package com.example.studyspringgradle.global.response.normal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ResponseCode {
    RESPONSE_OK(200, "COMP001"),
    CREATE_COMPLETE(201, "COMP002"),

    BAD_REQUEST(400, "ERR000"),
    CONFLICT(409, "ERR001"),
    WRONG_ACCOUNT_FORMAT(400, "ERR002"),
    WRONG_PASSWORD(400, "ERR003"),

    ;

    private final String code;
    private final int status;

    ResponseCode(int status, String code) {
        this.status = status;
        this.code = code;
    }
}
