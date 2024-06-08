package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountBasicService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationDaoService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.converter.api.IConverterToEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OperationServiceImpl implements IOperationService {

    private final IOperationDaoService operationDaoService;
    private final IAccountBasicService accountBasicService;
    private final IConverterToEntity converterToEntity;

    private final static Logger logger = LogManager.getLogger();

    public OperationServiceImpl(IOperationDaoService operationDaoService, IAccountBasicService accountBasicService, IConverterToEntity converterToEntity) {
        this.operationDaoService = operationDaoService;
        this.accountBasicService = accountBasicService;
        this.converterToEntity = converterToEntity;
    }

    @Override
    @Transactional
    public void create(UUID uuid, OperationCreateDTO operationCreateDTO) {

        checkFields(operationCreateDTO);

        if (accountBasicService.isExist(uuid)){
            UUID currencyUuid = operationCreateDTO.getCurrency();
            UUID categoryUuid = operationCreateDTO.getCategory();

            //CHECK UUID x2

            OperationEntity operationEntity = converterToEntity.convert(operationCreateDTO);

            operationEntity.setUuid(UUID.randomUUID());
            LocalDateTime localDateTime = LocalDateTime.now();
            operationEntity.setDtCreate(localDateTime);
            operationEntity.setDtUpdate(localDateTime);
            operationEntity.setAccountUuid(accountBasicService.getByUuid(uuid).get());

            operationDaoService.create(operationEntity);

            logger.log(Level.INFO, "Operation created successfully");

        }else {
            throw new IllegalArgumentException("Account uuid not exist");
        }
    }

    @Override
    @Transactional
    public void checkFields(OperationCreateDTO operationCreateDTO) {
        if (operationCreateDTO.getDate() ==null || operationCreateDTO.getDescription() == null
            || operationCreateDTO.getCategory() == null || operationCreateDTO.getValue() == null
            || operationCreateDTO.getCurrency() == null){
            throw new IllegalArgumentException("Create operation has empty field");
        }
    }

    @Override
    public Page<OperationEntity> getPage(UUID uuid, Pageable pageable) {
        return null;
    }
}
