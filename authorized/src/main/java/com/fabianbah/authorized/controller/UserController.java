package com.fabianbah.authorized.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabianbah.authorized.dtos.UserPasswordDto;
import com.fabianbah.authorized.entity.Password;
import com.fabianbah.authorized.entity.User;
import com.fabianbah.authorized.service.PasswordService;
import com.fabianbah.authorized.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
@RestController
@RequestMapping( path = "/users/" )
public class UserController {

    private UserService userService;
    private PasswordService passwordService;

    @PostMapping("/newUser")
    ResponseEntity<User> createNewUser( @RequestBody UserPasswordDto userAndPassword ){
        User newUser = userService.createUser(userAndPassword);
        passwordService.createPassword( userAndPassword.getPassword(), newUser );

        return new ResponseEntity<>( newUser, HttpStatus.CREATED );
    };

    @PutMapping("/updateUser")
    ResponseEntity<User> updatedUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);

        return new ResponseEntity<>( updatedUser, HttpStatus.OK );
    }

    @GetMapping("/getAllUsers")
    ResponseEntity<List<User>> getAllUsers() {
        List<User> updatedUser = userService.getAllUsers();

        return new ResponseEntity<>( updatedUser, HttpStatus.OK );
    }

    @GetMapping("/getUserByIdentification/{identificationId}")
    ResponseEntity<User> getUserByIdentificationId( @PathVariable("identificationId") Long identificationId ) {
        Optional<User> user = userService.getUserByIdentificationId( identificationId );

        return new ResponseEntity<>( user.get(), HttpStatus.OK );
    }
}
