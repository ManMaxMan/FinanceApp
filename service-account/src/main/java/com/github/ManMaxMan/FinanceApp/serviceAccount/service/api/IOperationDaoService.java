package com.github.ManMaxMan.FinanceApp.serviceAccount.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;

public interface IOperationDaoService {
    void create (OperationEntity operationEntity);
}
