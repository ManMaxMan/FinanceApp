package com.github.ManMaxMan.FinanceApp.serviceAccount.service.converter;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.converter.api.IConverterToEntity;
import org.springframework.stereotype.Component;

@Component
public class ConvertToEntity implements IConverterToEntity {
    @Override
    public AccountEntity convert(AccountCreateDTO accountCreateDTO) {

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setTitle(accountCreateDTO.getTitle());
        accountEntity.setDescription(accountCreateDTO.getDescription());
        accountEntity.setCurrencyUuid(accountCreateDTO.getCurrency());
        accountEntity.setTypeAccount(accountCreateDTO.getType());

        return accountEntity;
    }

    @Override
    public OperationEntity convert(OperationCreateDTO operationCreateDTO) {

        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setDescription(operationCreateDTO.getDescription());
        operationEntity.setCurrencyUuid(operationCreateDTO.getCurrency());
        operationEntity.setCategoryUuid(operationCreateDTO.getCategory());
        operationEntity.setValue(operationCreateDTO.getValue());
        operationEntity.setDtExecute(operationCreateDTO.getDate());

        return operationEntity;
    }
}
