package com.fabianbah.gateway.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fabianbah.gateway.dtos.ErrorDto;

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
}
