package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.TokenDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserLoginDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.ILoginService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.utils.JwtTokenHandler;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.utils.UserHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class LoginServiceImpl implements ILoginService {

    private final IUserService userService;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtTokenHandler;
    private final UserHolder userHolder;

    public LoginServiceImpl(IUserService userService, PasswordEncoder encoder, JwtTokenHandler jwtTokenHandler, UserHolder userHolder) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtTokenHandler = jwtTokenHandler;
        this.userHolder = userHolder;
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {

        Optional<UserEntity> optional = userService.getByMail(userLoginDTO.getMail());
        if (optional.isPresent()){
            String encodedPassword = optional.get().getPassword();

            if (!encoder.matches(userLoginDTO.getPassword(), encodedPassword)) {
                throw new IllegalArgumentException();
            }

            UserDetails userDetails = new User(optional.get().getMail(), optional.get().getPassword(),
                    Collections.emptyList());

            return jwtTokenHandler.generateAccessToken(userDetails);
        }else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserEntity getUser(TokenDTO token) {

        UserDetails userDetails = userHolder.getUser();

        Optional<UserEntity> optional = userService.getByMail(userDetails.getUsername());

        if (optional.isPresent()){
            return optional.get();
        }else {
            throw new IllegalArgumentException();
        }
    }
}
