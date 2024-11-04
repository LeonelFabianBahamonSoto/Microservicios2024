package com.fabianbah.auth_server.repositories;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabianbah.auth_server.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, BigInteger>{
    Optional<Customer> findByEmail(String email);
}
