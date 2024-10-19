package com.fabianbah.authorized.service;

import java.util.List;
import java.util.Optional;

import com.fabianbah.authorized.dtos.UserPasswordDto;
import com.fabianbah.authorized.entity.User;

public interface UserService {
    User createUser( UserPasswordDto userAndPassword );

    User updateUser( User user );

    Optional<User> getUserByIdentificationId( Long identificationId );

    List<User> getAllUsers();

    void deleteUser( Long identificationId );
}
