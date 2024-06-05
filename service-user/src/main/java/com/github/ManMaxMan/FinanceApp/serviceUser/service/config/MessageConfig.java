package com.github.ManMaxMan.FinanceApp.serviceUser.service.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "message")
@Getter
@Setter
public class MessageConfig {
    private String server;
    private String subject;
    private String username;
}
