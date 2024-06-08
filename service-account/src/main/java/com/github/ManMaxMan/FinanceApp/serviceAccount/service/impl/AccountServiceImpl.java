package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.UpdateAccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.enums.EAccountType;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountBasicService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountDaoService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.converter.api.IConverterToEntity;
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

    private final static Logger logger = LogManager.getLogger();

    public AccountServiceImpl(IAccountDaoService accountDaoService, IAccountBasicService accountBasicService, IConverterToEntity converterToEntity) {
        this.accountDaoService = accountDaoService;
        this.accountBasicService = accountBasicService;
        this.converterToEntity = converterToEntity;

    }

    @Override
    @Transactional
    public void create(AccountCreateDTO accountCreateDTO) {

        checkFields(accountCreateDTO);

        //CHECK CurrencyUuid

        AccountEntity entity = converterToEntity.convert(accountCreateDTO);
        entity.setUuid(UUID.randomUUID());
        LocalDateTime localDateTime = LocalDateTime.now();
        entity.setDtCreate(localDateTime);
        entity.setDtUpdate(localDateTime);
        entity.setBalance(0);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //UserUuid
        entity.setUserUuid(UUID.randomUUID());

        accountDaoService.create(entity);

        logger.log(Level.INFO, "Account created successfully");
    }

    @Override
    @Transactional
    public Page<AccountEntity> getPage(Pageable pageable) {

        //!!!!!!!!!!!!!!!!
        //getUserUUID

        //!!!

        Page<AccountEntity> page = accountDaoService.getByUserUuid(pageable, null);

        logger.log(Level.INFO, "Get page of account successfully");

        return page;
    }

    @Override
    @Transactional
    public AccountEntity getByUuid(UUID uuid) {

        Optional<AccountEntity> optional = accountBasicService.getByUuid(uuid);

        if (optional.isPresent()){
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
            //CHECK currency uuid!!!!!!!!!!!!!!

            AccountEntity accountEntityDb = optional.get();

            if (!accountEntityDb.getDtUpdate().equals(updateAccountDTO.getDtUpdate())) {
                throw new OptimisticLockException("Несоответствие версий. Данные обновлены другим пользователем," +
                        "попробуйте ещё раз.");
            }

            accountEntityDb.setTitle(accountCreate.getTitle());
            accountEntityDb.setDescription(accountCreate.getDescription());
            accountEntityDb.setTypeAccount(accountCreate.getType());
            accountEntityDb.setCurrencyUuid(accountCreate.getCurrency());
            accountEntityDb.setDtUpdate(updateAccountDTO.getDtUpdate());

            accountDaoService.create(accountEntityDb);

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
