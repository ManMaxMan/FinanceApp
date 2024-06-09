package com.github.ManMaxMan.FinanceApp.serviceAccount.controller.converter.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.PageAccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.PageOperationDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import org.springframework.data.domain.Page;

public interface IConverterToDTO {
    PageAccountDTO convertAccount (Page<AccountEntity> pageEntities);
    AccountDTO convertAccount (AccountEntity accountEntity);
    PageOperationDTO convertOperation (Page<OperationEntity> pageEntities);
    OperationDTO convertOperation (OperationEntity operationEntity);
}
