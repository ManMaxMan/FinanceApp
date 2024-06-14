package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

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
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.api.AuditClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.enums.ETypeEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.impl.security.UserDetailsImpl;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.utils.UserHolder;
import jakarta.persistence.OptimisticLockException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final AuditClientFeign auditClient;
    private final UserHolder userHolder;

    private final static Logger logger = LogManager.getLogger();

    public ManagementServiceImpl(IUserService userService, IConverterToEntity converterToEntity, VerifyCodeConfig verifyCodeConfig, PasswordEncoder encoder, AuditClientFeign auditClient, UserHolder userHolder) {
        this.userService = userService;
        this.converterToEntity = converterToEntity;
        this.verifyCodeConfig = verifyCodeConfig;
        this.encoder = encoder;
        this.auditClient = auditClient;
        this.userHolder = userHolder;
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

        if (userService.isExist(userAdd.getMail())) {
            throw new IllegalArgumentException("Add user has existing user");
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

        UserDetailsImpl userDetails = (UserDetailsImpl) userHolder.getUser();

        AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                .type(ETypeEntity.USER)
                .uuidUser(userDetails.getUuid())
                .uuidEntity(userEntity.getUuid())
                .text("User add")
                .build();

        auditClient.createAuditAction(auditCreateDTO);

        logger.log(Level.INFO, "User successfully add");
    }

    @Override
    @Transactional
    public UserEntity getUser(UUID uuid) {

        Optional<UserEntity> optional = userService.getByUuid(uuid);

        UserDetailsImpl userDetails = (UserDetailsImpl)(userHolder.getUser());

        if (optional.isPresent()){

            if (!userDetails.getRoleFrom().equals(EUserRole.SYSTEM)){
                AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                        .type(ETypeEntity.USER)
                        .uuidUser(userDetails.getUuid())
                        .uuidEntity(optional.get().getUuid())
                        .text("Get information about user by UUID")
                        .build();

                auditClient.createAuditAction(auditCreateDTO);
            }

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

            if(!userDb.getDtUpdate().withNano(0).equals(updateDTO.getDtUpdate().withNano(0))){
                throw new OptimisticLockException("Несоответствие версий. Данные обновлены другим пользователем," +
                        "попробуйте ещё раз.");
            }

            userDb.setDtUpdate(updateDTO.getDtUpdate());
            userDb.setPassword(encoder.encode(userUpdate.getPassword()));
            userDb.setFio(userUpdate.getFio());
            userDb.setRole(userUpdate.getRole());

            if (Objects.equals(userDb.getMail(), userUpdate.getMail())){
                EUserStatus oldStatus = userDb.getStatus();
                EUserStatus newStatus = userUpdate.getStatus();
                userDb.setStatus(newStatus);
                if (!oldStatus.equals(newStatus)){
                    if (EUserStatus.WAITING_ACTIVATION.equals(newStatus)){
                        userDb.setVerificationEntity(generateVerificationEntity());
                    }
                }
            }else {
                userDb.setMail(userUpdate.getMail());
                userDb.setStatus(EUserStatus.WAITING_ACTIVATION);
                userDb.setVerificationEntity(generateVerificationEntity());
            }

            userService.create(userDb);

            UserDetailsImpl userDetails = (UserDetailsImpl)(userHolder.getUser());

            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.USER)
                    .uuidUser(userDetails.getUuid())
                    .uuidEntity(optional.get().getUuid())
                    .text("Update user")
                    .build();

            auditClient.createAuditAction(auditCreateDTO);

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
    @Transactional
    public PageOfUserEntityDTO generate(PageOfUserEntityDTO pageOfUser) {
        Page<UserEntity> pageUsers = userService.getAll(pageOfUser.getNumber(),
                pageOfUser.getSize());
        pageOfUser.setFirst(pageUsers.isFirst());
        pageOfUser.setLast(pageUsers.isLast());
        pageOfUser.setTotalPages(pageUsers.getTotalPages());
        pageOfUser.setTotalElements(pageUsers.getTotalElements());
        pageOfUser.setNumberOfElements(pageUsers.getNumberOfElements());
        pageOfUser.setContent(pageUsers.getContent());

        UserDetailsImpl userDetails = (UserDetailsImpl)(userHolder.getUser());

        pageOfUser.getContent().forEach(entity->{
            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.REPORT)
                    .uuidUser(userDetails.getUuid())
                    .uuidEntity(entity.getUuid())
                    .text("Get information about user in page")
                    .build();
            auditClient.createAuditAction(auditCreateDTO);
        });

        logger.log(Level.INFO, "Get page users");

        return pageOfUser;
    }
}
