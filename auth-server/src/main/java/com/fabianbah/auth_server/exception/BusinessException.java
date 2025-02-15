package com.fabianbah.auth_server.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final HttpStatus httpStatusCode;
    private final LocalDate errorDate;
    private final String message;

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.errorDate = LocalDate.now();
        this.httpStatusCode = httpStatus;
        this.message = message;
    }
}
