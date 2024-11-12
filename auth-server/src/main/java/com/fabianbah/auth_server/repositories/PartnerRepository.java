package com.fabianbah.auth_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabianbah.auth_server.entities.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByClientId(String clientID);
}
