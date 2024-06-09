package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl.data;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.api.IOperationRepository;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationDaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
public class OperationDaoServiceImpl implements IOperationDaoService {

    private final IOperationRepository operationRepository;

    public OperationDaoServiceImpl(IOperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public void create(OperationEntity operationEntity) {
        operationRepository.saveAndFlush(operationEntity);
    }

    @Override
    public Page<OperationEntity> getByAccountUuid(Pageable pageable, AccountEntity account) {
        return operationRepository.findAllByAccountUuid(pageable, account);
    }

    @Override
    public Boolean isExist(UUID operationUuid) {
        return operationRepository.existsByUuid(operationUuid);
    }

    @Override
    public Optional<OperationEntity> getByUuid(UUID operationUuid) {
        return operationRepository.findByUuid(operationUuid);
    }

    @Override
    public void delete(UUID operationUuid) {
        operationRepository.deleteByUuid(operationUuid);
    }
}
