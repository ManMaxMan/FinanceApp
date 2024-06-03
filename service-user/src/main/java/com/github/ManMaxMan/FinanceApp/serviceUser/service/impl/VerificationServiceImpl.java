package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;


import com.github.ManMaxMan.FinanceApp.serviceUser.dao.api.IVerificationRepository;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.VerificationEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IMessageSenderService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IVerificationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VerificationServiceImpl implements IVerificationService {

    private final static Integer CODE_LENGTH = 25;
    private final static Boolean USE_LETTERS = true;
    private final static Boolean USE_NUMBERS = true;

    private final IVerificationRepository verificationRepository;

    public VerificationServiceImpl(IVerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

    @Override
    @Transactional
    public void createCode(UUID uuid) {

        String code = RandomStringUtils.random(CODE_LENGTH, USE_LETTERS, USE_NUMBERS);
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setUuid(uuid);
        verificationEntity.setVerificationCode(code);

        verificationRepository.saveAndFlush(verificationEntity);
    }

    @Override
    public VerificationEntity findByUuid(UUID uuid) {
        return verificationRepository.findByUUID(uuid);
    }


}
