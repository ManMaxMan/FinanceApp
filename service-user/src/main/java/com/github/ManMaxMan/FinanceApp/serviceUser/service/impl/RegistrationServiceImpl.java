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
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IVerificationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RegistrationServiceImpl implements IRegistrationService {

    private final static Integer CODE_LENGTH = 25;
    private final static Boolean USE_LETTERS = true;
    private final static Boolean USE_NUMBERS = true;


    private final IConverterToEntity converterToEntity;
    private final IUserService userService;


    public RegistrationServiceImpl(IConverterToEntity converterToEntity, IUserService userService) {
        this.converterToEntity=converterToEntity;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserEntity registration(UserRegistrationDTO registrationDTO) {

        if (registrationDTO.getPassword() == null || registrationDTO.getFio() == null ||
                registrationDTO.getMail() == null) {
            throw new IllegalArgumentException();
        }

        if (!EmailValidator.getInstance().isValid(registrationDTO.getMail())){
            throw new IllegalArgumentException();
        }

        UserEntity userEntity = converterToEntity.convert(registrationDTO);
        userEntity.setUuid(UUID.randomUUID());
        LocalDateTime dateTime = LocalDateTime.now();
        userEntity.setDtCreate(dateTime);
        userEntity.setDtUpdate(dateTime);
        userEntity.setRole(EUserRole.USER);
        userEntity.setStatus(EUserStatus.WAITING_ACTIVATION);

        String code = RandomStringUtils.random(CODE_LENGTH, USE_LETTERS, USE_NUMBERS);
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setVerificationCode(code);
        verificationEntity.setMessageStatus(EMessageStatus.LOAD);
        userEntity.setVerificationEntity(verificationEntity);

        userEntity=userService.create(userEntity);

        return userEntity;
    }

    @Override
    @Transactional
    public void verification(VerificationDTO verificationDTO) {
        Optional<UserEntity> optional = userService.getByMail(verificationDTO.getEmail());
        if(optional.isPresent()){

            if (optional.get().getStatus() != EUserStatus.WAITING_ACTIVATION) {
                throw new IllegalArgumentException();
            }
            if (optional.get().getVerificationEntity().getMessageStatus()!=EMessageStatus.OK) {
                throw new IllegalArgumentException();
            }

            if(Objects.equals(optional.get().getVerificationEntity().getVerificationCode(),
                    verificationDTO.getVerificationCode())) {
                optional.get().setStatus(EUserStatus.ACTIVATED);
                userService.create(optional.get());
            }else {
                throw new IllegalArgumentException();
            }
        }else {
            throw new IllegalArgumentException();
        }
    }
}
