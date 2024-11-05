package com.fabianbah.auth_server.security;

import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fabianbah.auth_server.repositories.CustomerRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final var userName = authentication.getName();
        final var pwd = authentication.getCredentials().toString();

        final var customerFromDb = customerRepository.findByEmail(userName);
        final var customer = customerFromDb.orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));
        final var customerPwd = customer.getPassword();

        if (passwordEncoder.matches(pwd, customerPwd)) {
            final var roles = customer.getRoles();
            final var authorities = roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(userName, pwd, authorities);
        } else {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
