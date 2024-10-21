package com.fabianbah.auth_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabianbah.auth_server.entities.UserAuth;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

}
