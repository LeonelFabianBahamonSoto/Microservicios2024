package com.fabianbah.authorized.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabianbah.authorized.entity.Password;

public interface PasswordRepository extends JpaRepository<Password, Integer> {
    // Optional<Password> findPasswordByUserId( Integer usersid );
}
