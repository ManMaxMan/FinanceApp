package com.github.ManMaxMan.FinanceApp.serviceAccount.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IOperationService {
    void create (UUID uuid, OperationCreateDTO operationCreateDTO);
    void checkFields (OperationCreateDTO operationCreateDTO);
    Page<OperationEntity> getPage (UUID uuid, Pageable pageable);
}
