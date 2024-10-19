package com.fabianbah.authorized.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDto {
    private Long identificationId;

    private String fullName;

    private String lastName;

    private String email;

    private String password;
}