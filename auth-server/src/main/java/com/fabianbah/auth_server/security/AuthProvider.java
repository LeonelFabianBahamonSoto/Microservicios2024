package com.fabianbah.auth_server.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fabianbah.auth_server.entities.Customer;
import com.fabianbah.auth_server.entities.Password;
import com.fabianbah.auth_server.entities.Role;
import com.fabianbah.auth_server.services.CustomerService;
import com.fabianbah.auth_server.services.PasswordService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    // private CustomerRepository customerRepository;
    private CustomerService customerService;
    private PasswordService passwordService;

    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String userEmail = authentication.getName();
        final String pwd = authentication.getCredentials().toString();
        Customer currentCustomer = new Customer();

        try {
            Customer getCustomer = customerService.getCustomerByEmail(userEmail);

            if( getCustomer == null ){
                throw new BadCredentialsException("User not found, Invalid Credentials");
            };

            currentCustomer = getCustomer;

        } catch (BadCredentialsException e) {
            new BadCredentialsException("User not found, Invalid Credentials");
            e.printStackTrace();
        } catch (Exception e) {
            new BadCredentialsException("User not found, Invalid Credentials");
            e.printStackTrace();
        }

        Password passwordEntity = new Password();
        try {
            passwordEntity = passwordService.getPasswordByCustomerId(currentCustomer.getCustomerId());
        } catch (Exception e) {
            new BadCredentialsException("Password not found, Invalid Credentials");
            e.printStackTrace();
        }

        final String customerPwd = passwordEntity.getPassword();
        final List<Role> roles = currentCustomer.getRoles();

        if (passwordEncoder.matches(pwd, customerPwd)) {
            // final var roles = currentUser.getRoles();
            final var authorities = roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(userEmail, pwd, authorities);
        } else {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
