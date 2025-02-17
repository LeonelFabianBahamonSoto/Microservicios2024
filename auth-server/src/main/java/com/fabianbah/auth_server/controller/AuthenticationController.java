package com.fabianbah.auth_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabianbah.auth_server.dtos.TokenDto;
import com.fabianbah.auth_server.entities.JWTRequest;
import com.fabianbah.auth_server.entities.JwtResponse;
import com.fabianbah.auth_server.services.AuthenticationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(path = "/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody JWTRequest jwtRequest) {

        JwtResponse jwtResponse = this.authenticationService.userAuthentication(jwtRequest);

        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("validateTokenAuth")
    public ResponseEntity<?> validateTokenAuth(@RequestHeader("accessToken") String token) {
        boolean isValidService = this.authenticationService.validateUserAuthenticationForService(token);

        if (!isValidService) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication credentials are invalid");
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(token);

        return ResponseEntity.ok(tokenDto);
    }
}
