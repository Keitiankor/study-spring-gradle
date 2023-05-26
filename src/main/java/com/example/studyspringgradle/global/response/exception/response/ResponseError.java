package com.example.studyspringgradle.global.response.exception.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseError {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private String timestamp;
    private int status;
    private String code;
    private List<FieldError> errors;

    private ResponseError(CodeError code, List<FieldError> errors) {
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.status = code.getStatus();
        this.errors = errors;
        this.code = code.getCode();
    }

    private ResponseError(CodeError code) {
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }

    public static ResponseError of(CodeError code, BindingResult bindingResult) {
        return new ResponseError(code, FieldError.of(bindingResult));
    }

    public static ResponseError of(CodeError code) {
        return new ResponseError(code);
    }

    public static ResponseError of(MethodArgumentTypeMismatchException e) {
        String value = e.getValue() == null ? "" : e.getValue().toString();
        List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ResponseError(CodeError.INVALID_INPUT_VALUE, errors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(String filed, String value, String reason) {
            this.field = filed;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(String field, String value, String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

    }

}
