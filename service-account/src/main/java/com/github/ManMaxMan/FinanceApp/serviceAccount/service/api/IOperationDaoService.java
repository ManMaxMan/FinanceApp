package com.github.ManMaxMan.FinanceApp.serviceAccount.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IOperationDaoService {
    void create (OperationEntity operationEntity);
    Page<OperationEntity> getByAccountUuid (Pageable pageable, AccountEntity account);
    Boolean isExist (UUID operationUuid);
    Optional<OperationEntity> getByUuid(UUID operationUuid);
    void delete (UUID operationUuid);
}
