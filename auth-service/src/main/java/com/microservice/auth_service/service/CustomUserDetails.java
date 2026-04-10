package com.microservice.auth_service.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.microservice.auth_service.model.UserCredentials;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(UserCredentials userCredentials) {
        this.username = userCredentials.getUsername();
        this.password = userCredentials.getPassword();
        // Mapping the role from DB to Spring Authority
        this.authorities = List.of(new SimpleGrantedAuthority(userCredentials.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Standard boiler-plate for security
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
