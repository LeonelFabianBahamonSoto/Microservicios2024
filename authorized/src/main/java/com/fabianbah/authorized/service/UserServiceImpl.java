package com.fabianbah.authorized.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fabianbah.authorized.dtos.UserPasswordDto;
import com.fabianbah.authorized.entity.User;
import com.fabianbah.authorized.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    @Transactional( readOnly = false )
    public User createUser( UserPasswordDto userAndPassword ){
        userRepository.findByIdentificationId( userAndPassword.getIdentificationId() )
            .ifPresent( u -> { throw new RuntimeException( "El usuario que se quiere crear ya existe" ); });

        LocalDate now = LocalDate.now();

        User newUser = new User();
        newUser.setIdentificationId(userAndPassword.getIdentificationId());
        newUser.setFullName(userAndPassword.getFullName());
        newUser.setLastName(userAndPassword.getLastName());
        newUser.setEmail(userAndPassword.getEmail());
        newUser.setCreationDate(now);

        return userRepository.save( newUser );
    }

    @Override
    @Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, DataIntegrityViolationException.class} )
    public User updateUser(User user) {
        return userRepository.save( user );
    }

    @Override
    @Transactional( readOnly = true )
    public Optional<User> getUserByIdentificationId(Long identificationId) {
        Optional<User> userAlreadyExist = userRepository.findByIdentificationId( identificationId );

        if( userAlreadyExist.isEmpty() ){
            throw new RuntimeException( "El usuario que se quiere no existe" );
        };

        return userAlreadyExist;
    }

    @Override
    @Transactional( readOnly = true )
    public List<User> getAllUsers() {
        List<User> allUser = userRepository.findAll();

        if( allUser.isEmpty() ){
            throw new RuntimeException( "No existen usuarios en la DB" );
        };

        return allUser;
    }

    @Override
    @Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, DataIntegrityViolationException.class} )
    public void deleteUser(Long identificationId) {
        userRepository.deleteByIdentificationId( identificationId );
    }

}
