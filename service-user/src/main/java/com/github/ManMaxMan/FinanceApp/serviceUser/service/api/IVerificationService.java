package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.VerificationEntity;

import java.util.UUID;

public interface IVerificationService {
    void createCode(UUID uuid);
    VerificationEntity findByUuid(UUID uuid);

}
