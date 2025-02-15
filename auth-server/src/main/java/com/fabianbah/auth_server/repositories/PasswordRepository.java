package com.fabianbah.auth_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabianbah.auth_server.entities.Password;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    // Optional<Password> findByCustomerId( long customerId );
    Optional<Password> findByCustomer_CustomerId(long customerId);

}
