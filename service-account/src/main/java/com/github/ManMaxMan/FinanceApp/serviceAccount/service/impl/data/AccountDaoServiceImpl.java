package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl.data;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.api.IAccountRepository;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountDaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountDaoServiceImpl implements IAccountDaoService {

    private final IAccountRepository accountRepository;

    public AccountDaoServiceImpl(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public void create(AccountEntity accountEntity) {
        accountRepository.saveAndFlush(accountEntity);
    }

    @Override
    @Transactional
    public Page<AccountEntity> getByUserUuid(Pageable pageable, UUID userUuid) {
        return accountRepository.findAllByUserUuid(pageable, userUuid);
    }

}
