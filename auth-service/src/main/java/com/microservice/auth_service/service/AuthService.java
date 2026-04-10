package com.microservice.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.auth_service.dao.UserCredentialDao;
import com.microservice.auth_service.model.UserCredentials;


@Service
public class AuthService {

    @Autowired
    private UserCredentialDao userCredentialDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<String> saveUser(UserCredentials userCredentials) {
        userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));

        if (userCredentials.getRole() == null) {
            userCredentials.setRole("ROLE_USER");
        }
        userCredentialDao.save(userCredentials);

        return new ResponseEntity<>("User saved sucessfully", HttpStatus.OK);
    }

    public String generateToken(String username) {
        // Fetch the user to get their assigned role for the JWT claim
        UserCredentials user = userCredentialDao.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return jwtService.generateToken(username, user.getRole());
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
