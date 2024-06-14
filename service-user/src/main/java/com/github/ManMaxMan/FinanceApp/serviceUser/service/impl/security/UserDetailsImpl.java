package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl.security;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserDetailsImpl implements UserDetails {

    private final UserEntity userEntity;
    private final EUserRole userRole;

    public UserDetailsImpl(UserEntity userEntity, EUserRole userRole) {
        this.userEntity = userEntity;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+userEntity.getRole());

        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return userEntity.getMail();
    }

    public UUID getUuid(){
        return userEntity.getUuid();
    }

    public EUserRole getRoleFrom() {
        return userRole;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
