package com.fabianbah.auth_server.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabianbah.auth_server.entities.Customer;
import com.fabianbah.auth_server.repositories.CustomerRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/aboutUs")
public class AboutUsController {

    private final CustomerRepository customerRepository;

    @GetMapping("/getCustomer/{email}")
    public ResponseEntity<Customer> welcome(@PathVariable("email") String email) throws Exception {

        Optional<Customer> customerFromDb = customerRepository.findByEmail(email);
        Customer customer = customerFromDb.orElseThrow(() -> new RuntimeException("Customer not found"));

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
