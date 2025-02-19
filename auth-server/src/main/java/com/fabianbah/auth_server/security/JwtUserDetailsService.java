package com.fabianbah.auth_server.security;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import com.fabianbah.auth_server.entities.User;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fabianbah.auth_server.entities.Customer;
import com.fabianbah.auth_server.entities.Password;
import com.fabianbah.auth_server.exception.BusinessException;
import com.fabianbah.auth_server.services.CustomerService;
import com.fabianbah.auth_server.services.PasswordService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    // private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final PasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        try {
            // Optional<Customer> customerFromDb =
            // this.customerRepository.findByEmail(userEmail);
            // Customer customer = customerFromDb.orElseThrow(() -> new
            // BadCredentialsException("User not found"));

            Customer customer = customerService.getCustomerByEmail(userEmail);
            Password passwordEntity = passwordService.getPasswordByCustomerId(customer.getCustomerId());

            final var pwd = passwordEntity.getPassword();
            final var roles = customer.getRoles();
            final var authorities = roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());

            return (UserDetails) new User(customer.getEmail(), pwd, authorities);
        } catch (Exception e) {
            throw new BusinessException("Get user information Error", HttpStatus.UNAUTHORIZED);
        }
    }
}