package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.VerificationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;

public interface IRegistrationService {
    UserEntity registration(UserRegistrationDTO registrationDTO);
    void verification (VerificationDTO verificationDTO);
}
