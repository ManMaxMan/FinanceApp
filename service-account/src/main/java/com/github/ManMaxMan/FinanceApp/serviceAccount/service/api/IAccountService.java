package com.github.ManMaxMan.FinanceApp.serviceAccount.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.UpdateAccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAccountService {
    void create(AccountCreateDTO accountCreateDTO);
    Page<AccountEntity> getPage(Pageable pageable);
    AccountEntity getByUuid(UUID uuid);
    void update(UpdateAccountDTO updateAccountDTO);
    void checkFields (AccountCreateDTO accountCreateDTO);
}
