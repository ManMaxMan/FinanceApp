package com.github.ManMaxMan.FinanceApp.serviceUser.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    //private final static String HOST = "smtp.mail.ru";
    //private final static Integer PORT = 587;
    private final static String FROM = System.getenv("JavaSendingEmail");
    private final static String PASSWORD = System.getenv("JavaSendingPassword");

    @Bean
    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        //mailSender.setHost(HOST);
        //mailSender.setPort(PORT);
        mailSender.setUsername(FROM);
        mailSender.setPassword(PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }


}
