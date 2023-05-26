package com.example.studyspringgradle.global.response.normal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ResponseCode {
    RESPONSE_OK(200, "COMP001"),
    CREATE_COMPLETE(201, "COMP002"),
    DELETE_COMPLETE(202, "COMP003"),
    CHANGE_COMPLETE(202, "COMP004"),


    ;

    private final String code;
    private final int status;

    ResponseCode(int status, String code) {
        this.status = status;
        this.code = code;
    }
}
