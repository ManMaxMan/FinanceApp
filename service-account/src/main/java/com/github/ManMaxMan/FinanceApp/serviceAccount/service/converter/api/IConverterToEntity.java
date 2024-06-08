package com.github.ManMaxMan.FinanceApp.serviceAccount.service.converter.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;

public interface IConverterToEntity {
    AccountEntity convert (AccountCreateDTO accountCreateDTO);
    OperationEntity convert (OperationCreateDTO operationCreateDTO);
}
