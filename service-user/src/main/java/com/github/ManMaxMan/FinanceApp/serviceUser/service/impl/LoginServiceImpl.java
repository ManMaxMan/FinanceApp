package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.TokenDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserLoginDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.ILoginService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import com.github.ManMaxMan.FinanceApp.serviceUser.controller.utils.JwtTokenHandler;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.api.AuditClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.enums.ETypeEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.impl.security.UserDetailsImpl;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.utils.UserHolder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class LoginServiceImpl implements ILoginService {

    private final IUserService userService;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtTokenHandler;
    private final UserHolder userHolder;
    private final AuditClientFeign auditClient;

    private final static Logger logger = LogManager.getLogger();

    public LoginServiceImpl(IUserService userService, PasswordEncoder encoder, JwtTokenHandler jwtTokenHandler, UserHolder userHolder, AuditClientFeign auditClient) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtTokenHandler = jwtTokenHandler;
        this.userHolder = userHolder;
        this.auditClient = auditClient;
    }

    @Override
    @Transactional
    public String login(UserLoginDTO userLoginDTO) {

        Optional<UserEntity> optional = userService.getByMail(userLoginDTO.getMail());
        if (optional.isPresent()){

            UserEntity userEntity = optional.get();

            if (!userEntity.getStatus().equals(EUserStatus.ACTIVATED)){
                throw new IllegalArgumentException("Login not activated users");
            }

            String encodedPassword = userEntity.getPassword();

            if (!encoder.matches(userLoginDTO.getPassword(), encodedPassword)) {
                throw new IllegalArgumentException("Login failed:"+userLoginDTO.getMail());
            }

            UserDetails userDetails = new User(userEntity.getMail(), userEntity.getPassword(),
                    Collections.emptyList());



            String token = jwtTokenHandler.generateAccessToken(userDetails, userEntity.getRole() );

            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.USER)
                    .uuidUser(userEntity.getUuid())
                    .uuidEntity(userEntity.getUuid())
                    .text("User login successfully")
                    .build();

            auditClient.createAuditAction(auditCreateDTO);

            logger.log(Level.INFO,"Login successful");

            return token;
        }else {
            throw new IllegalArgumentException("Login failed: "+userLoginDTO.getMail());
        }
    }

    @Override
    @Transactional
    public UserEntity getUser(TokenDTO token) {

        UserDetailsImpl userDetails = (UserDetailsImpl) userHolder.getUser();

        Optional<UserEntity> optional = userService.getByMail(userDetails.getUsername());

        if (optional.isPresent()){

            if (!userDetails.getRoleFrom().equals(EUserRole.SYSTEM)){
                AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                        .type(ETypeEntity.USER)
                        .uuidUser(optional.get().getUuid())
                        .uuidEntity(optional.get().getUuid())
                        .text("User get information about me")
                        .build();

                auditClient.createAuditAction(auditCreateDTO);
            }


            logger.log(Level.INFO,"Get info about user "+userDetails.getUsername());
            return optional.get();
        }else {
            throw new IllegalArgumentException("Not get info about user "+userDetails.getUsername());
        }
    }
}
