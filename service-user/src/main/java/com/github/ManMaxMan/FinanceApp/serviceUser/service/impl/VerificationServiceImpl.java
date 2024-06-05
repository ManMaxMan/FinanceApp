package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceUser.dao.api.IVerificationRepository;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.VerificationEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IVerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VerificationServiceImpl implements IVerificationService {

    private final IVerificationRepository verificationRepository;

    public VerificationServiceImpl(IVerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

    @Override
    @Transactional
    public void update(VerificationEntity verificationEntity) {
        verificationRepository.saveAndFlush(verificationEntity);
    }
}
