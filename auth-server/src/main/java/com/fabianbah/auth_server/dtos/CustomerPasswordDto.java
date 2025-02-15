package com.fabianbah.auth_server.dtos;

import lombok.Data;

@Data
public class CustomerPasswordDto {
    private long identificationId;
    private String fullName;
    private String lastName;
    private String email;
    private String password;
    private String roleName;
}