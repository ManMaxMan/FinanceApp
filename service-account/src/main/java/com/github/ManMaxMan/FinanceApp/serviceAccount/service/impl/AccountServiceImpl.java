package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.utils.UserDetailsImpl;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.UpdateAccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.enums.EAccountType;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountBasicService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountDaoService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.converter.api.IConverterToEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api.AuditClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api.ClassifierClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.enums.ETypeEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.utils.UserHolder;
import jakarta.persistence.OptimisticLockException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements IAccountService {

    private final IAccountDaoService accountDaoService;
    private final IAccountBasicService accountBasicService;
    private final IConverterToEntity converterToEntity;
    private final AuditClientFeign auditClientFeign;
    private final UserHolder userHolder;
    private final ClassifierClientFeign classifierClientFeign;

    private final static Logger logger = LogManager.getLogger();

    public AccountServiceImpl(IAccountDaoService accountDaoService, IAccountBasicService accountBasicService, IConverterToEntity converterToEntity, AuditClientFeign auditClientFeign, UserHolder userHolder, ClassifierClientFeign classifierClientFeign) {
        this.accountDaoService = accountDaoService;
        this.accountBasicService = accountBasicService;
        this.converterToEntity = converterToEntity;

        this.auditClientFeign = auditClientFeign;
        this.userHolder = userHolder;

        this.classifierClientFeign = classifierClientFeign;
    }

    @Override
    @Transactional
    public void create(AccountCreateDTO accountCreateDTO) {

        checkFields(accountCreateDTO);

        UserDetailsImpl userDetails = (UserDetailsImpl) userHolder.getUser();

        if(!classifierClientFeign.isExistCurrency(userDetails.getCurrentHeaderToken(),
                accountCreateDTO.getCurrency())) {
            throw new IllegalArgumentException("Currency is not exist");
        }

        AccountEntity entity = converterToEntity.convert(accountCreateDTO);
        entity.setUuid(UUID.randomUUID());
        LocalDateTime localDateTime = LocalDateTime.now();
        entity.setDtCreate(localDateTime);
        entity.setDtUpdate(localDateTime);
        entity.setBalance(0);

        entity.setUserUuid(UUID.fromString(userDetails.getUsername()));

        accountDaoService.create(entity);

        AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                .type(ETypeEntity.ACCOUNT)
                .uuidUser(UUID.fromString(userDetails.getUsername()))
                .uuidEntity(entity.getUuid())
                .text("Create account")
                .build();
        auditClientFeign.createAuditAction(auditCreateDTO);

        logger.log(Level.INFO, "Account created successfully");
    }

    @Override
    @Transactional
    public Page<AccountEntity> getPage(Pageable pageable) {

        UserDetailsImpl userDetails = (UserDetailsImpl) userHolder.getUser();

        Page<AccountEntity> page = accountDaoService.getByUserUuid(pageable,
                UUID.fromString(userDetails.getUsername()));

        page.getContent().forEach(entity->{
            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.REPORT)
                    .uuidUser(UUID.fromString(userDetails.getUsername()))
                    .uuidEntity(entity.getUuid())
                    .text("Get account by uuid in page")
                    .build();
            auditClientFeign.createAuditAction(auditCreateDTO);
        });

        logger.log(Level.INFO, "Get page of account successfully");

        return page;
    }

    @Override
    @Transactional
    public AccountEntity getByUuid(UUID uuid) {

        Optional<AccountEntity> optional = accountBasicService.getByUuid(uuid);

        if (optional.isPresent()){

            UserDetailsImpl userDetails = (UserDetailsImpl) userHolder.getUser();

            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.ACCOUNT)
                    .uuidUser(UUID.fromString(userDetails.getUsername()))
                    .uuidEntity(optional.get().getUuid())
                    .text("Get account by uuid")
                    .build();
            auditClientFeign.createAuditAction(auditCreateDTO);

            logger.log(Level.INFO, "Get account by uuid successfully");
            return optional.get();
        }else {
            throw new IllegalArgumentException("Account uuid not exist");
        }
    }

    @Override
    @Transactional
    public void update(UpdateAccountDTO updateAccountDTO) {

        AccountCreateDTO accountCreate = updateAccountDTO.getAccountCreateDTO();

        checkFields(accountCreate);

        Optional<AccountEntity> optional = accountBasicService.getByUuid(updateAccountDTO.getUuid());
        if (optional.isPresent()){

            UserDetailsImpl userDetails = (UserDetailsImpl) userHolder.getUser();

            if(!classifierClientFeign.isExistCurrency(userDetails.getCurrentHeaderToken(),
                    updateAccountDTO.getAccountCreateDTO().getCurrency())) {
                throw new IllegalArgumentException("Currency is not exist");
            }

            AccountEntity accountEntityDb = optional.get();

            if (!accountEntityDb.getDtUpdate().withNano(0).equals(updateAccountDTO.getDtUpdate().withNano(0))) {
                throw new OptimisticLockException("Несоответствие версий. Данные обновлены другим пользователем," +
                        "попробуйте ещё раз.");
            }

            accountEntityDb.setTitle(accountCreate.getTitle());
            accountEntityDb.setDescription(accountCreate.getDescription());
            accountEntityDb.setTypeAccount(accountCreate.getType());
            accountEntityDb.setCurrencyUuid(accountCreate.getCurrency());
            accountEntityDb.setDtUpdate(updateAccountDTO.getDtUpdate());

            accountDaoService.create(accountEntityDb);

            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.ACCOUNT)
                    .uuidUser(UUID.fromString(userDetails.getUsername()))
                    .uuidEntity(accountEntityDb.getUuid())
                    .text("Update account by uuid")
                    .build();
            auditClientFeign.createAuditAction(auditCreateDTO);

            logger.log(Level.INFO, "Update account by uuid successfully");

        }else {
            throw new IllegalArgumentException("Account uuid not exist");
        }
    }

    @Override
    @Transactional
    public void checkFields(AccountCreateDTO accountCreateDTO) {
        if (accountCreateDTO.getTitle() == null || accountCreateDTO.getType() == null ||
                accountCreateDTO.getDescription() == null ||accountCreateDTO.getCurrency() == null) {
            throw new IllegalArgumentException("Create account has empty field");
        }

        if (!EnumUtils.isValidEnum(EAccountType.class, accountCreateDTO.getType().toString())){
            throw new IllegalArgumentException("Create account has invalid status");
        }
    }
}
