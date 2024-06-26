package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.VerificationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.VerificationEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IConverterToEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IRegistrationService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.config.VerifyCodeConfig;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.api.AuditClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.enums.ETypeEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.utils.UserHolder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RegistrationServiceImpl implements IRegistrationService {

    private final IConverterToEntity converterToEntity;
    private final IUserService userService;
    private final VerifyCodeConfig verifyCodeConfig;
    private final AuditClientFeign auditClient;

    private final static Logger logger = LogManager.getLogger();


    public RegistrationServiceImpl(IConverterToEntity converterToEntity, IUserService userService, VerifyCodeConfig verifyCodeConfig, AuditClientFeign auditClient) {
        this.converterToEntity=converterToEntity;
        this.userService = userService;
        this.verifyCodeConfig = verifyCodeConfig;
        this.auditClient = auditClient;
    }

    @Override
    @Transactional
    public void registration(UserRegistrationDTO registrationDTO) {

        if (registrationDTO.getPassword() == null || registrationDTO.getFio() == null ||
                registrationDTO.getMail() == null) {
            throw new IllegalArgumentException("Registration user has empty fields");
        }

        if (!EmailValidator.getInstance().isValid(registrationDTO.getMail())){
            throw new IllegalArgumentException("Registration user has invalid email address");
        }

        if (userService.isExist(registrationDTO.getMail())) {
            throw new IllegalArgumentException("Registration user has existing user");
        }

        UserEntity userEntity = converterToEntity.convert(registrationDTO);
        userEntity.setUuid(UUID.randomUUID());
        LocalDateTime dateTime = LocalDateTime.now();
        userEntity.setDtCreate(dateTime);
        userEntity.setDtUpdate(dateTime);
        userEntity.setRole(EUserRole.USER);
        userEntity.setStatus(EUserStatus.WAITING_ACTIVATION);

        String code = RandomStringUtils.random(verifyCodeConfig.getLength(),
                verifyCodeConfig.getLetters(), verifyCodeConfig.getNumbers());
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setVerificationCode(code);
        verificationEntity.setMessageStatus(EMessageStatus.LOAD);
        userEntity.setVerificationEntity(verificationEntity);

        userService.create(userEntity);


        AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                .type(ETypeEntity.USER)
                .uuidUser(userEntity.getUuid())
                .uuidEntity(userEntity.getUuid())
                .text("User registration successfully")
                .build();

        auditClient.createAuditAction(auditCreateDTO);

        logger.log(Level.INFO, "Registration user successfully created");

    }

    @Override
    @Transactional
    public void verification(VerificationDTO verificationDTO) {
        Optional<UserEntity> optional = userService.getByMail(verificationDTO.getEmail());
        if(optional.isPresent()){

            UserEntity userEntity = optional.get();

            if (userEntity.getStatus() != EUserStatus.WAITING_ACTIVATION) {
                throw new IllegalArgumentException("Verification user not waiting activation");
            }
            if (userEntity.getVerificationEntity().getMessageStatus()!=EMessageStatus.OK) {
                throw new IllegalArgumentException("Verification user suspicious! Verification message_status: "
                        +userEntity.getVerificationEntity().getMessageStatus());
            }

            if(Objects.equals(userEntity.getVerificationEntity().getVerificationCode(),
                    verificationDTO.getVerificationCode())) {
                userEntity.setStatus(EUserStatus.ACTIVATED);
                userService.create(userEntity);

                AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                        .type(ETypeEntity.USER)
                        .uuidUser(userEntity.getUuid())
                        .uuidEntity(userEntity.getVerificationEntity().getUuid())
                        .text("User verification successfully")
                        .build();

                auditClient.createAuditAction(auditCreateDTO);

                logger.log(Level.INFO, "Verification user successfully verified");
            }else {
                throw new IllegalArgumentException("Verification user present, but verification code is different");
            }
        }else {
            throw new IllegalArgumentException("Verification user has incorrect email address");
        }
    }
}
