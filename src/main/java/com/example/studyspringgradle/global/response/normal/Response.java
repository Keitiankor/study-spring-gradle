package com.example.studyspringgradle.global.response.normal;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Response implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss.SSS")
    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("status")
    private int status;

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private Object data;

    private Response(ResponseCode code, Object data) {
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.status = code.getStatus();
        this.code = code.getCode();
        this.data = data;
    }

    private Response(ResponseCode code){
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    public static Response of(ResponseCode code, Object data) {
        return new Response(code, data);
    }

    
    public static Response of(ResponseCode code) {
        return new Response(code);
    }
}
