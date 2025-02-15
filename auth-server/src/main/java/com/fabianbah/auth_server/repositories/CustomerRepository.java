package com.fabianbah.auth_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabianbah.auth_server.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Optional<Customer> findByIdentificationIdOrEmail(long identificationId, String email);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByIdentificationId(long identificationId);

    void deleteByIdentificationId(long identificationId);
}
