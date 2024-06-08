package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl.data;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.api.IAccountRepository;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountBasicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountBasicServiceImpl implements IAccountBasicService {

    private final IAccountRepository accountRepository;

    public AccountBasicServiceImpl(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Boolean isExist(UUID uuid) {
        return accountRepository.existsByUuid(uuid);
    }

    @Override
    @Transactional
    public Optional<AccountEntity> getByUuid(UUID uuid) {
        return accountRepository.findByUuid(uuid);
    }
}
