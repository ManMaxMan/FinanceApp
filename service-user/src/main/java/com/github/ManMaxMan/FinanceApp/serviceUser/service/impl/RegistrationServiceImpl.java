package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.VerificationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IConverter;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IRegistrationService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IVerificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements IRegistrationService {

    private final IConverter converter;
    private final IUserService userService;
    private final IVerificationService verificationService;

    public RegistrationServiceImpl(IConverter converter, IUserService userService, IVerificationService verificationService) {
        this.converter = converter;
        this.userService = userService;
        this.verificationService = verificationService;
    }


    @Override
    public UserEntity registration(UserRegistrationDTO registrationDTO) {

        if (registrationDTO.getPassword() == null || registrationDTO.getFio() == null ||
                registrationDTO.getMail() == null) {
            throw new IllegalArgumentException();
        }

        UserEntity userEntity = converter.convert(registrationDTO);
        LocalDateTime dateTime = LocalDateTime.now();
        userEntity.setDtCreate(dateTime);
        userEntity.setDtUpdate(dateTime);
        userEntity.setRole(EUserRole.USER);
        userEntity.setStatus(EUserStatus.WAITING_ACTIVATION);

        userEntity=userService.create(userEntity);
        verificationService.createCode(userEntity.getUuid());

        return userEntity;
    }

    @Override
    public void verification(VerificationDTO verificationDTO) {


    }
}
