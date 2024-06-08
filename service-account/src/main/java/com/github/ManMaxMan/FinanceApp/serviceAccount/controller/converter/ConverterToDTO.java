package com.github.ManMaxMan.FinanceApp.serviceAccount.controller.converter;

import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.PageAccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.PageOperationDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ConverterToDTO implements IConverterToDTO {


    @Override
    public PageAccountDTO convert(Page<AccountEntity> pageEntities) {
        return PageAccountDTO.builder()
                .number(pageEntities.getNumber())
                .size(pageEntities.getSize())
                .totalPages(pageEntities.getTotalPages())
                .totalElements(pageEntities.getTotalElements())
                .first(pageEntities.isFirst())
                .numberOfElements(pageEntities.getNumberOfElements())
                .last(pageEntities.isLast())
                .content(pageEntities.getContent().stream().map(this::convert).toList())
                .build();
    }

    @Override
    public AccountDTO convert(AccountEntity accountEntity) {
        return AccountDTO.builder()
                .uuid(accountEntity.getUuid())
                .dtCreate(accountEntity.getDtCreate())
                .dtUpdate(accountEntity.getDtUpdate())
                .title(accountEntity.getTitle())
                .description(accountEntity.getDescription())
                .balance(accountEntity.getBalance())
                .type(accountEntity.getTypeAccount())
                .currency(accountEntity.getCurrencyUuid())
                .build();
    }

    @Override
    public PageOperationDTO convert(Page<OperationEntity> pageEntities) {
        return PageOperationDTO.builder()
                .number(pageEntities.getNumber())
                .size(pageEntities.getSize())
                .totalPages(pageEntities.getTotalPages())
                .totalElements(pageEntities.getTotalElements())
                .first(pageEntities.isFirst())
                .numberOfElements(pageEntities.getNumberOfElements())
                .last(pageEntities.isLast())
                .content(pageEntities.getContent().stream().map(this::convert).toList())
                .build();
    }

    @Override
    public OperationDTO convert(OperationEntity operationEntity) {
        return OperationDTO.builder()
                .uuid(operationEntity.getUuid())
                .dtCreate(operationEntity.getDtCreate())
                .dtUpdate(operationEntity.getDtUpdate())
                .date(operationEntity.getDtExecute())
                .description(operationEntity.getDescription())
                .category(operationEntity.getCategoryUuid())
                .value(operationEntity.getValue())
                .currency(operationEntity.getCurrencyUuid())
                .build();
    }
}
