package com.fabianbah.auth_server.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CustomerRoleDto {
    private Long customerId;

    private String email;

    private String password;

    private String roleName;
}