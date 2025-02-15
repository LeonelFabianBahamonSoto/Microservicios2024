package com.fabianbah.auth_server.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fabianbah.auth_server.entities.Password;
import com.fabianbah.auth_server.repositories.CustomerRepository;
import com.fabianbah.auth_server.services.PasswordService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class CustomerUserDetails implements UserDetailsService {

    // private final CustomerRepository customerRepository;
    private CustomerRepository customerRepository;
    private PasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return this.customerRepository.findByEmail(email)
                .map(customer -> {
                    List<GrantedAuthority> authorities = customer.getRoles()
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                            .collect(Collectors.toList());

                    Password passwordEntity = new Password();
                    try {
                        passwordEntity = passwordService.getPasswordByCustomerId(customer.getCustomerId());
                    } catch (Exception e) {
                        new BadCredentialsException("Password not found, Invalid Credentials");
                        e.printStackTrace();
                    }

                    return new User(customer.getEmail(), passwordEntity.getPassword(), authorities);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
