package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserEntityDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UpdateDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserAddUpdateDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.VerificationEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IConverterToEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IManagementService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.config.VerifyCodeConfig;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ManagementServiceImpl implements IManagementService {

    private final IUserService userService;
    private final IConverterToEntity converterToEntity;
    private final VerifyCodeConfig verifyCodeConfig;
    private final PasswordEncoder encoder;

    private final static Logger logger = LogManager.getLogger();

    public ManagementServiceImpl(IUserService userService, IConverterToEntity converterToEntity, VerifyCodeConfig verifyCodeConfig, PasswordEncoder encoder) {
        this.userService = userService;
        this.converterToEntity = converterToEntity;
        this.verifyCodeConfig = verifyCodeConfig;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void addUser(UserAddUpdateDTO userAdd) {

        if (userAdd.getPassword() == null || userAdd.getFio() == null ||
                userAdd.getMail() == null || userAdd.getStatus()==null ||
                userAdd.getRole() == null) {
            throw new IllegalArgumentException("Add user has empty fields");
        }

        if (!EmailValidator.getInstance().isValid(userAdd.getMail())){
            throw new IllegalArgumentException("Add user has invalid email address");
        }

        if (!EnumUtils.isValidEnum(EUserStatus.class, userAdd.getStatus().toString())){
            throw new IllegalArgumentException("Add user has invalid status");
        }

        if (!EnumUtils.isValidEnum(EUserRole.class, userAdd.getRole().toString())){
            throw new IllegalArgumentException("Add user has invalid role");
        }

        UserEntity userEntity = converterToEntity.convert(userAdd);
        userEntity.setUuid(UUID.randomUUID());
        LocalDateTime dateTime = LocalDateTime.now();
        userEntity.setDtCreate(dateTime);
        userEntity.setDtUpdate(dateTime);


        String code = RandomStringUtils.random(verifyCodeConfig.getLength(),
                verifyCodeConfig.getLetters(), verifyCodeConfig.getNumbers());
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setVerificationCode(code);

        if (userEntity.getStatus() == EUserStatus.WAITING_ACTIVATION){
            verificationEntity.setMessageStatus(EMessageStatus.LOAD);
        } else {
            verificationEntity.setMessageStatus(EMessageStatus.NONE);
        }

        userEntity.setVerificationEntity(verificationEntity);

        userService.create(userEntity);

        logger.log(Level.INFO, "User successfully add");
    }

    @Override
    @Transactional
    public UserEntity getUser(UUID uuid) {

        Optional<UserEntity> optional = userService.getByUuid(uuid);

        if (optional.isPresent()){
            logger.log(Level.INFO, "Get information about user by UUID");
            return optional.get();
        }else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    @Transactional
    public void updateUser(UpdateDTO updateDTO) {

        UserAddUpdateDTO userAddUpdate = updateDTO.getUserAddUpdateDTO();

        if (userAddUpdate.getFio()==null||userAddUpdate.getStatus()==null||
            userAddUpdate.getRole()==null||userAddUpdate.getPassword()==null||
            userAddUpdate.getMail()==null){
            throw new IllegalArgumentException("Update user has empty fields");
        }

        if (!EmailValidator.getInstance().isValid(userAddUpdate.getMail())){
            throw new IllegalArgumentException("Update user has invalid email address");
        }

        if (!EnumUtils.isValidEnum(EUserStatus.class, userAddUpdate.getStatus().toString())){
            throw new IllegalArgumentException("Update user has invalid status");
        }

        if (!EnumUtils.isValidEnum(EUserRole.class, userAddUpdate.getRole().toString())){
            throw new IllegalArgumentException("Update user has invalid role");
        }

        Optional<UserEntity> optional = userService.getByUuid(updateDTO.getUuid());
        if (optional.isPresent()){
            UserEntity userDb = optional.get();
            UserEntity userUpdate = converterToEntity.convert(userAddUpdate);

            userDb.setDtUpdate(updateDTO.getDtUpdate());
            userDb.setPassword(encoder.encode(userUpdate.getPassword()));
            userDb.setFio(userUpdate.getFio());
            userDb.setRole(userUpdate.getRole());
            if (Objects.equals(userDb.getMail(), userUpdate.getMail())){
                switch (userDb.getStatus()){
                    case WAITING_ACTIVATION:{
                        switch (userUpdate.getStatus()){
                            case WAITING_ACTIVATION:{break;}
                            case DEACTIVATED:{
                                userDb.setStatus(EUserStatus.DEACTIVATED);
                                break;
                            }
                            case ACTIVATED:{
                                userDb.setStatus(EUserStatus.ACTIVATED);
                                break;
                            }
                        }
                        break;
                    }
                    case ACTIVATED:{
                        switch (userUpdate.getStatus()){
                            case WAITING_ACTIVATION:{
                                userDb.setStatus(EUserStatus.WAITING_ACTIVATION);
                                userDb.setVerificationEntity(generateVerificationEntity());
                                break;
                            }
                            case DEACTIVATED:{
                                userDb.setStatus(EUserStatus.DEACTIVATED);
                                break;
                            }
                            case ACTIVATED:{break;}
                        }
                        break;
                    }
                    case DEACTIVATED:{
                        switch (userUpdate.getStatus()){
                            case WAITING_ACTIVATION:{
                                userDb.setStatus(EUserStatus.WAITING_ACTIVATION);
                                userDb.setVerificationEntity(generateVerificationEntity());
                                break;
                            }
                            case DEACTIVATED:{break;}
                            case ACTIVATED:{
                                userDb.setStatus(EUserStatus.ACTIVATED);
                                break;
                            }
                        }
                        break;
                    }
                }
            }else {
                userDb.setMail(userUpdate.getMail());
                userDb.setStatus(EUserStatus.WAITING_ACTIVATION);
                userDb.setVerificationEntity(generateVerificationEntity());
            }

            userService.create(userDb);

            logger.log(Level.INFO, "Update user successfully");
        }else {
            throw new IllegalArgumentException("User for update not found");
        }
    }

    @Override
    @Transactional
    public VerificationEntity generateVerificationEntity() {
        String code = RandomStringUtils.random(verifyCodeConfig.getLength(),
                verifyCodeConfig.getLetters(), verifyCodeConfig.getNumbers());
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setVerificationCode(code);
        verificationEntity.setMessageStatus(EMessageStatus.LOAD);
        return verificationEntity;
    }

    @Override
    public PageOfUserEntityDTO generate(PageOfUserEntityDTO pageOfUser) {
        Page<UserEntity> pageUsers = userService.getAll(pageOfUser.getNumber(),
                pageOfUser.getSize());
        pageOfUser.setFirst(pageUsers.isFirst());
        pageOfUser.setLast(pageUsers.isLast());
        pageOfUser.setTotalPages(pageUsers.getTotalPages());
        pageOfUser.setTotalElements(pageUsers.getTotalElements());
        pageOfUser.setNumberOfElements(pageUsers.getNumberOfElements());
        pageOfUser.setContent(pageUsers.getContent());

        return pageOfUser;
    }
}
