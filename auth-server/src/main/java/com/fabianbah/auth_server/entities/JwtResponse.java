package com.fabianbah.auth_server.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {
    private String jwt;
}
