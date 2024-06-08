package com.github.ManMaxMan.FinanceApp.serviceAccount.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IAccountDaoService {
    void create (AccountEntity accountEntity);
    Page<AccountEntity> getByUserUuid(Pageable pageable, UUID userUuid);
}
