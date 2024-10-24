package com.fabianbah.auth_server.Services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fabianbah.auth_server.dtos.TokenDto;
import com.fabianbah.auth_server.dtos.UserAuthDto;
import com.fabianbah.auth_server.entities.UserAuth;
import com.fabianbah.auth_server.helpers.JwtHelper;
import com.fabianbah.auth_server.repositories.UserAuthRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    @Override
    public TokenDto login(UserAuthDto userAuthDto) {
        UserAuth userAuthFromDb = this.userAuthRepository.findByUsername( userAuthDto.getUserNamer() )
            .orElseThrow(() -> new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Usuario no autorizado" ) );

        this.validPassword(userAuthDto, userAuthFromDb);

        return TokenDto.builder().accessToken(
                this.jwtHelper.createToken( userAuthFromDb.getUserNamer() )
            )
            .build();
    }

    @Override
    public TokenDto validateToken(TokenDto tokenDto) {

        if( this.jwtHelper.validateToken( tokenDto.getAccessToken() ) ){
            return TokenDto.builder().accessToken( tokenDto.getAccessToken() ).build();
        };

        throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Usuario no autorizado" );
    }

    @Override
    public void validPassword( UserAuthDto userAuthDto, UserAuth userAuth ) {
        if( this.passwordEncoder.matches( userAuthDto.getPassword(), userAuth.getPassword() )){
            throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Usuario no autorizado" );
        }
    }

}
