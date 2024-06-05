package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;

public interface IMessageSenderService {
    void sendingVerificationMessage();
    EMessageStatus send(String code, String mailTo, String fio);
}
