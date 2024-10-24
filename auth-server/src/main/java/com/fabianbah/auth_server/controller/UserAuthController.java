package com.fabianbah.auth_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabianbah.auth_server.Services.UserAuthService;
import com.fabianbah.auth_server.dtos.TokenDto;
import com.fabianbah.auth_server.dtos.UserAuthDto;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping(path="auth")
@RestController
public class UserAuthController {
    private UserAuthService userAuthService;

    @PostMapping(path="login")
    public ResponseEntity<TokenDto> jwtCreate( @RequestBody UserAuthDto userAuthDto ){

        TokenDto tokenDto = this.userAuthService.login( userAuthDto );

        return new ResponseEntity<>( tokenDto, HttpStatus.OK );
    };

    @PostMapping(path="jwt")
    public ResponseEntity<TokenDto> jwtValidate( @RequestHeader String accessToken ){

        TokenDto tokenDto = this.userAuthService.validateToken(
            TokenDto.builder().accessToken(accessToken).build()
        );

        return new ResponseEntity<>( tokenDto, HttpStatus.OK );
    }
}
