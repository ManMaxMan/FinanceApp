package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

public interface IMessageSenderService {
    void sendingVerificationMessage();
    void send(String code, String mailTo);
}
