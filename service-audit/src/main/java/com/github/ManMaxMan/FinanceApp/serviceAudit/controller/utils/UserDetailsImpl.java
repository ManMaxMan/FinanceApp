package com.github.ManMaxMan.FinanceApp.serviceAudit.controller.utils;


import com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final UserDTO userDTO;
    private final String token;

    public UserDetailsImpl(UserDTO userDTO, String token) {
        this.userDTO = userDTO;
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+userDTO.getRole());

        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return userDTO.getUuid().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getCurrentHeaderToken(){
        return this.token;
    }

    public String getMail(){
        return userDTO.getMail();
    }
}
