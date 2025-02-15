package com.fabianbah.authorized.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fabianbah.authorized.entity.Password;
import com.fabianbah.authorized.entity.User;
import com.fabianbah.authorized.repository.PasswordRepository;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private PasswordRepository passwordRepository;

    // @Autowired
    // private SecurityService securityService;

    @Override
    @Transactional( readOnly = false )
    public Password savePassword( Password newPassword ) {
        return passwordRepository.save( newPassword );
    };

    @Override
    @Transactional( readOnly = false )
    public Password createPassword( String password, User user ){

        if( password.isEmpty() || password == null ){
            throw new IllegalArgumentException("La contraseña no cumple con los requisitos.");
        };

        Password newPassword = new Password();
        // String hashingPass = securityService.passwordHash(password);
        // newPassword.setPassword( hashingPass );
        newPassword.setPassword( password );
        newPassword.setCreationDate( user.getCreationDate() );
        newPassword.setUser( user );

        return savePassword( newPassword );
    }

    @Override
    @Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, DataIntegrityViolationException.class} )
    public Password updatePassword(Password password) {
        return passwordRepository.save( password );
    }

}
