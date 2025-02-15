package com.fabianbah.auth_server.exception;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fabianbah.auth_server.entities.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorDto> handleBusinessException(BusinessException businessException) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(businessException.getMessage())
                .errorDate(businessException.getErrorDate())
                .build();

        return ResponseEntity
                .status(businessException.getHttpStatusCode())
                .body(errorDto);
    };

    @ExceptionHandler(value = JwtAuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(
            JwtAuthenticationException jwtAuthenticationException) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(jwtAuthenticationException.getMessage())
                .errorDate(LocalDate.now())
                .build();

        return ResponseEntity
                .status(jwtAuthenticationException.getHttpStatus())
                .body(errorDto);
    }
}
