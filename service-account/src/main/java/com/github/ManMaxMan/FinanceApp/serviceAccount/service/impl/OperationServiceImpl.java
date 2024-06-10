package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationTaskDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountBasicService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationDaoService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationService;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.converter.api.IConverterToEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api.AuditClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api.CategoryClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api.CurrencyClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.enums.ETypeEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.utils.UserHolder;
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
    private final AuditClientFeign auditClientFeign;
    private final UserHolder userHolder;
    private final CurrencyClientFeign currencyClientFeign;
    private final CategoryClientFeign categoryClientFeign;

    private final static Logger logger = LogManager.getLogger();

    public OperationServiceImpl(IOperationDaoService operationDaoService, IAccountBasicService accountBasicService, IConverterToEntity converterToEntity, AuditClientFeign auditClientFeign, UserHolder userHolder, CurrencyClientFeign currencyClientFeign, CategoryClientFeign categoryClientFeign) {
        this.operationDaoService = operationDaoService;
        this.accountBasicService = accountBasicService;
        this.converterToEntity = converterToEntity;
        this.auditClientFeign = auditClientFeign;
        this.userHolder = userHolder;
        this.currencyClientFeign = currencyClientFeign;
        this.categoryClientFeign = categoryClientFeign;
    }

    @Override
    @Transactional
    public void create(UUID uuid, OperationCreateDTO operationCreateDTO) {

        checkFields(operationCreateDTO);

        if (accountBasicService.isExist(uuid)){
            UUID currencyUuid = operationCreateDTO.getCurrency();
            UUID categoryUuid = operationCreateDTO.getCategory();

            //CHECK UUID x2
            if(!currencyClientFeign.isExistCurrency(userHolder.getUser().getPassword(),
                    currencyUuid)) {
                throw new IllegalArgumentException("Currency is not exist");
            }
            if(!categoryClientFeign.isExistCategory(userHolder.getUser().getPassword(),
                    categoryUuid)) {
                throw new IllegalArgumentException("Category is not exist");
            }

            OperationEntity operationEntity = converterToEntity.convert(operationCreateDTO);

            operationEntity.setUuid(UUID.randomUUID());
            LocalDateTime localDateTime = LocalDateTime.now();
            operationEntity.setDtCreate(localDateTime);
            operationEntity.setDtUpdate(localDateTime);
            operationEntity.setAccountUuid(accountBasicService.getByUuid(uuid).get());

            operationDaoService.create(operationEntity);

            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.OPERATION)
                    .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                    .uuidEntity(operationEntity.getUuid())
                    .text("Create operation")
                    .build();
            auditClientFeign.createAuditAction(userHolder.getUser().getPassword(),auditCreateDTO);

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

        page.getContent().forEach(entity->{
            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.OPERATION)
                    .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                    .uuidEntity(entity.getUuid())
                    .text("Get operation for account in page")
                    .build();
            auditClientFeign.createAuditAction(userHolder.getUser().getPassword(),auditCreateDTO);
        });

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

                    AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                            .type(ETypeEntity.OPERATION)
                            .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                            .uuidEntity(operationTaskDTO.getOperationUuid())
                            .text("Operation delete")
                            .build();
                    auditClientFeign.createAuditAction(userHolder.getUser().getPassword(),auditCreateDTO);

                    logger.log(Level.INFO, "Operation delete successfully");
                }
                case UPDATE ->{
                    OperationCreateDTO operationCreateDTO = operationTaskDTO.getOperationCreateDTO();
                    checkFields(operationCreateDTO);

                    UUID categoryUuid = operationCreateDTO.getCategory();
                    UUID currencyUuid = operationCreateDTO.getCurrency();

                    //CHECK UUID x2
                    if(!currencyClientFeign.isExistCurrency(userHolder.getUser().getPassword(),
                            currencyUuid)) {
                        throw new IllegalArgumentException("Currency is not exist");
                    }
                    if(!categoryClientFeign.isExistCategory(userHolder.getUser().getPassword(),
                            categoryUuid)) {
                        throw new IllegalArgumentException("Category is not exist");
                    }

                    operationEntityDb.setDtExecute(operationCreateDTO.getDate());
                    operationEntityDb.setDescription(operationCreateDTO.getDescription());
                    operationEntityDb.setCategoryUuid(categoryUuid);
                    operationEntityDb.setValue(operationCreateDTO.getValue());
                    operationEntityDb.setCurrencyUuid(currencyUuid);

                    operationDaoService.create(operationEntityDb);

                    AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                            .type(ETypeEntity.OPERATION)
                            .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                            .uuidEntity(operationEntityDb.getUuid())
                            .text("Operation update")
                            .build();
                    auditClientFeign.createAuditAction(userHolder.getUser().getPassword(),auditCreateDTO);

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
