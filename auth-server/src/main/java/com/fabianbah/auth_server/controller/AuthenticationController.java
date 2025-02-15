package com.fabianbah.auth_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabianbah.auth_server.entities.JWTRequest;
import com.fabianbah.auth_server.entities.JwtResponse;
import com.fabianbah.auth_server.security.JwtUserDetailsService;
import com.fabianbah.auth_server.services.JwtService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(path = "/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> postToken(@RequestBody JWTRequest jwtRequest) {
        this.authenticate(jwtRequest);

        final var userDetails = this.jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token = this.jwtService.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt(token);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void authenticate(JWTRequest jwtRequest) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException | DisabledException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
