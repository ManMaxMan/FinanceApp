package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationTaskDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.enums.EOperationTask;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountBasicService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationDaoService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.converter.api.IConverterToEntity;
import jakarta.persistence.OptimisticLockException;
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
    public Page<OperationEntity> getPage(UUID uuid, Pageable pageable) {

        if (!accountBasicService.isExist(uuid)){
            throw new IllegalArgumentException("Account uuid not exist");
        }

        Page<OperationEntity> page = operationDaoService.getByAccountUuid(
                pageable, accountBasicService.getByUuid(uuid).get());

        logger.log(Level.INFO, "Get page of account successfully");

        return page;
    }

    @Override
    @Transactional
    public void task(OperationTaskDTO operationTaskDTO) {

        if(operationTaskDTO.getDtUpdate() == null){
            throw new IllegalArgumentException("Date update is null");
        }

        if (!accountBasicService.isExist(operationTaskDTO.getAccountUuid())){
            throw new IllegalArgumentException("Account uuid not exist");
        }

        if (!operationDaoService.isExist(operationTaskDTO.getOperationUuid())){
            throw new IllegalArgumentException("Operation uuid not exist");
        }

        Optional<OperationEntity> optionalOperation = operationDaoService.getByUuid(operationTaskDTO.getOperationUuid());
        if (optionalOperation.isPresent()){

            OperationEntity operationEntityDb = optionalOperation.get();

            if (!operationEntityDb.getDtUpdate().equals(operationTaskDTO.getDtUpdate())) {
                throw new OptimisticLockException("Несоответствие версий. Данные обновлены другим пользователем," +
                        "попробуйте ещё раз.");
            }

            switch (operationTaskDTO.getOperationTask()){
                case DELETE ->{
                    operationDaoService.delete(operationTaskDTO.getOperationUuid());
                    logger.log(Level.INFO, "Operation delete successfully");
                }
                case UPDATE ->{
                    OperationCreateDTO operationCreateDTO = operationTaskDTO.getOperationCreateDTO();
                    checkFields(operationCreateDTO);

                    UUID categoryUuid = operationCreateDTO.getCategory();
                    UUID currencyUuid = operationCreateDTO.getCurrency();

                    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //CHECK UUID x2

                    operationEntityDb.setDtExecute(operationCreateDTO.getDate());
                    operationEntityDb.setDescription(operationCreateDTO.getDescription());
                    operationEntityDb.setCategoryUuid(categoryUuid);
                    operationEntityDb.setValue(operationCreateDTO.getValue());
                    operationEntityDb.setCurrencyUuid(currencyUuid);

                    operationDaoService.create(operationEntityDb);

                    logger.log(Level.INFO, "Operation update successfully");
                }
            }
        }else {
            throw new IllegalArgumentException("Operation uuid deleted another user");
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
}