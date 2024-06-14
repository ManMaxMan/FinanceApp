package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.impl.security.UserDetailsImpl;

public interface ISecurityService {
    UserDetailsImpl getUserDetails(String mail, String strUserRole);
}
