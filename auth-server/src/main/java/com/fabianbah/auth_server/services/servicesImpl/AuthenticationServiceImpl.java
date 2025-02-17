package com.fabianbah.auth_server.services.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fabianbah.auth_server.entities.JWTRequest;
import com.fabianbah.auth_server.entities.JwtResponse;
import com.fabianbah.auth_server.security.JwtUserDetailsService;
import com.fabianbah.auth_server.services.AuthenticationService;
import com.fabianbah.auth_server.services.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    public JwtResponse userAuthentication(JWTRequest jwtRequest) {
        this.authenticateValidator(jwtRequest);

        final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = this.jwtService.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt(token);

        return jwtResponse;
    }

    @Override
    public boolean validateUserAuthenticationForService(String token) {
        try {
            String userName = this.jwtService.getUsernameFromToken(token);
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);
            boolean isJwtValid = jwtService.validateToken(token, userDetails);

            if (!isJwtValid) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void authenticateValidator(JWTRequest jwtRequest) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException | DisabledException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
