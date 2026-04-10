package com.microservice.auth_service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.auth_service.model.UserCredentials;

public interface UserCredentialDao extends JpaRepository<UserCredentials, Integer> {

    Optional<UserCredentials> findByUsername(String username);
}
