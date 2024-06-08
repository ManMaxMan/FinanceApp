package com.github.ManMaxMan.FinanceApp.serviceAccount.service.impl.data;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.api.IOperationRepository;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationDaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
}
