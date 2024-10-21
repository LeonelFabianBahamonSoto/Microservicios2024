package com.fabianbah.auth_server.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fabianbah.auth_server.dtos.TokenDto;
import com.fabianbah.auth_server.dtos.UserAuthDto;
import com.fabianbah.auth_server.repositories.UserAuthRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private UserAuthRepository userAuthRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDto login(UserAuthDto userAuthDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public TokenDto validateToken(TokenDto tokenDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

}
