package com.fabianbah.auth_server.Services;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class UserAuthServiceImpl implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;
    // private final PasswordEncoder passwordEncoder;
    // private final JwtHelper jwtHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserAuth> userObtained = userAuthRepository.findByUsername( username );

        if( userObtained == null ){
			log.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
        };

        UserAuth user = userObtained.get();

		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRolesName()))
				.peek(authority -> log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());

        return new User(
            user.getFullName(),
            user.getPassword().getUserPassword(),
            user.getUserEnable(),
            true,
            true,
            true,
            authorities
        );
    }

    // @Override
    // public TokenDto login(UserAuthDto userAuthDto) {
    //     UserAuth userAuthFromDb = this.userAuthRepository.findByUsername( userAuthDto.getUserName() )
    //         .orElseThrow(() -> new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Usuario no autorizado" ) );

    //     this.validPassword(userAuthDto, userAuthFromDb);

    //     return TokenDto.builder().accessToken(
    //             this.jwtHelper.createToken( userAuthFromDb.getUserName() )
    //         )
    //         .build();
    // }

    // @Override
    // public TokenDto validateToken(TokenDto tokenDto) {

    //     if( this.jwtHelper.validateToken( tokenDto.getAccessToken() ) ){
    //         return TokenDto.builder().accessToken( tokenDto.getAccessToken() ).build();
    //     };

    //     throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Usuario no autorizado" );
    // }

    // @Override
    // public void validPassword( UserAuthDto userAuthDto, UserAuth userAuth ) {
    //     if( this.passwordEncoder.matches( userAuthDto.getPassword(), userAuth.getPassword() )){
    //         throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Usuario no autorizado" );
    //     }
    // }

}
