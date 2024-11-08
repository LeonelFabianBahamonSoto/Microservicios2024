package com.fabianbah.auth_server.security;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fabianbah.auth_server.entities.Customer;
import com.fabianbah.auth_server.repositories.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<Customer> customerFromDb = this.customerRepository.findByEmail(username);
            Customer customer = customerFromDb.orElseThrow(() -> new BadCredentialsException("User not found"));

            final var pwd = customer.getPassword();
            final var roles = customer.getRoles();
            final var authorities = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

            return new User(customer.getEmail(), pwd, authorities);
        } catch (Exception e) {
            throw new BadCredentialsException("Request error");
        }
    }
}