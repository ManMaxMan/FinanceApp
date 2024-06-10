package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import org.springframework.security.core.userdetails.UserDetails;

public interface ISecurityService {
    UserDetails getUserDetails(String mail, String token);
}
