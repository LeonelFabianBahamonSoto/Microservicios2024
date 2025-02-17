package com.fabianbah.auth_server.services;

import com.fabianbah.auth_server.entities.JWTRequest;
import com.fabianbah.auth_server.entities.JwtResponse;

public interface AuthenticationService {
    JwtResponse userAuthentication(JWTRequest jwtRequest);

    boolean validateUserAuthenticationForService(String Token);

    void authenticateValidator(JWTRequest jwtRequest);
}
