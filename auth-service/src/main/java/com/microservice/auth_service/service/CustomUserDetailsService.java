package com.microservice.auth_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.microservice.auth_service.dao.UserCredentialDao;
import com.microservice.auth_service.model.UserCredentials;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialDao UserCredentialDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> credential = UserCredentialDao.findByUsername(username);

        // Convert our DB UserCredentials into a Spring Security UserDetails object
        return credential.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));
    }

}
