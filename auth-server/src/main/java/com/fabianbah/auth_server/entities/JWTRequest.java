package com.fabianbah.auth_server.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class JWTRequest implements Serializable {
    private String username;
    private String password;
}
