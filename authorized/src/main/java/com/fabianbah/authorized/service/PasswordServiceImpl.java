package com.fabianbah.authorized.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fabianbah.authorized.entity.Password;
import com.fabianbah.authorized.repository.PasswordRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private PasswordRepository passwordRepository;

    @Override
    @Transactional( readOnly = false )
    public Password createPassword(Password password){
        return passwordRepository.save( password );
    }

    @Override
    @Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, DataIntegrityViolationException.class} )
    public Password updatePassword(Password password) {
        return passwordRepository.save( password );
    }

}
