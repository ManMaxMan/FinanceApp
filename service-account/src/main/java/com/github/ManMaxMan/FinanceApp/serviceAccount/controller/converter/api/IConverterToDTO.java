package com.github.ManMaxMan.FinanceApp.serviceAccount.controller.converter.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.PageAccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.PageOperationDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import org.springframework.data.domain.Page;

public interface IConverterToDTO {
    PageAccountDTO convert (Page<AccountEntity> pageEntities);
    AccountDTO convert (AccountEntity accountEntity);
    PageOperationDTO convert (Page<OperationEntity> pageEntities);
    OperationDTO convert (OperationEntity operationEntity);
}
