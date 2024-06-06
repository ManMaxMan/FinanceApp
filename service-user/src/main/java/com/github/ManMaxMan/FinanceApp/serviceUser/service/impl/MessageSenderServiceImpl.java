package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IMessageSenderService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IVerificationService;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.config.MessageConfig;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MessageSenderServiceImpl implements IMessageSenderService {

    private final IUserService userService;
    private final IVerificationService verificationService;
    private final JavaMailSender mailSender;
    private final MessageConfig messageConfig;

    public MessageSenderServiceImpl(IUserService userService, IVerificationService verificationService, JavaMailSender mailSender, MessageConfig messageConfig) {
        this.userService = userService;
        this.verificationService = verificationService;
        this.mailSender = mailSender;
        this.messageConfig = messageConfig;
    }

    @Scheduled(fixedRate = 20000)
    @Override
    @Transactional
    public void sendingVerificationMessage(){
        List<UserEntity> users = userService.
                getByUserStatusAndMessageStatus(EUserStatus.WAITING_ACTIVATION, EMessageStatus.LOAD);
        users.forEach(user -> {
            String verificationCode = user.getVerificationEntity().getVerificationCode();
            EMessageStatus messageStatus = send(verificationCode, user.getMail(), user.getFio());
            user.getVerificationEntity().setMessageStatus(messageStatus);
            verificationService.update(user.getVerificationEntity());

        });
    }

    @Override
    public EMessageStatus send(String code, String mailTo, String fio) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setFrom(messageConfig.getUsername());
        message.setSubject(messageConfig.getSubject());

        message.setText(fio+ "! Перейдите по ссылке для подтверждения регистрации: " +
                "http://"+messageConfig.getServer()+"/api/"+messageConfig.getVersion()
                +"/cabinet/verification?code=" + code + "&mail=" + mailTo);

        try {
            mailSender.send(message);
            return EMessageStatus.OK;
        }catch (MailException exception){
            return EMessageStatus.ERROR;
        }
    }
}
