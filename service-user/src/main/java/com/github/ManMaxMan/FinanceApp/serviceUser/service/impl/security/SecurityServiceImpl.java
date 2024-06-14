package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl.security;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.ISecurityService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class SecurityServiceImpl implements ISecurityService {

    private final IUserService userService;

    public SecurityServiceImpl(IUserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetailsImpl getUserDetails(String mail, String strUserRole) {

        Optional<UserEntity> optional = userService.getByMail(mail);

        if (optional.isPresent()){
            return new UserDetailsImpl(optional.get(), EUserRole.valueOf(strUserRole));
        }else {
            return null;
        }
    }
}
