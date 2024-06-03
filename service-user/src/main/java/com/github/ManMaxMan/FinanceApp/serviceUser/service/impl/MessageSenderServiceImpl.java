package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IMessageSenderService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IVerificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageSenderServiceImpl implements IMessageSenderService {

    private final static String EMAIL_FROM = System.getenv("JavaSendingEmail");
    private final static String SUBJECT = "FinanceApp";


    private final IUserService userService;
    private final IVerificationService verificationService;
    private final JavaMailSender mailSender;

    public MessageSenderServiceImpl(IUserService userService, IVerificationService verificationService, JavaMailSender mailSender) {
        this.userService = userService;
        this.verificationService = verificationService;
        this.mailSender = mailSender;
    }

    @Scheduled(fixedRate = 20000)
    @Override
    public void sendingVerificationMessage(){
        List<UserEntity> users = userService.getByStatus(EUserStatus.WAITING_ACTIVATION);
        users.forEach(user -> {
            String verificationCode = verificationService.findByUuid(user.getUuid()).getVerificationCode();
            send(verificationCode, user.getMail());
        });
    }

    @Override
    public void send(String code, String mailTo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setFrom(EMAIL_FROM);
        message.setSubject(SUBJECT);
        message.setText("Перейдите по ссылке для подтверждения регистрации: " +
                "https://localhost:8080/cabinet/verification?code=" + code + "&mail=" + mailTo);

        mailSender.send(message);
    }
}
