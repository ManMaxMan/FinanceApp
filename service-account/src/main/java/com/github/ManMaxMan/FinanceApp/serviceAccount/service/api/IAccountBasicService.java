package com.github.ManMaxMan.FinanceApp.serviceAccount.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;

import java.util.Optional;
import java.util.UUID;

public interface IAccountBasicService {
    Boolean isExist (UUID uuid);
    Optional<AccountEntity> getByUuid(UUID uuid);
}
