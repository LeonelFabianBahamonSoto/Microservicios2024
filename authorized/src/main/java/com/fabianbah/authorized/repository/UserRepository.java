package com.fabianbah.authorized.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabianbah.authorized.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByIdentificationId( Long identificationId );

    void deleteByIdentificationId( Long identificationId );
}
