package com.fabianbah.auth_server.entities;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private String code;
    private String message;
    private LocalDate errorDate;
}
