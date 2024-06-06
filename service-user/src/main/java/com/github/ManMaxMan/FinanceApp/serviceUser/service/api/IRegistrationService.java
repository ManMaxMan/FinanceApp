package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.VerificationDTO;

public interface IRegistrationService {
    void registration(UserRegistrationDTO registrationDTO);
    void verification (VerificationDTO verificationDTO);
}
