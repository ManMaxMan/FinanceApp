package com.github.ManMaxMan.FinanceApp.serviceUser.service.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "verify-code")
@Getter
@Setter
public class VerifyCodeConfig {
    private Integer length;
    private Boolean letters;
    private Boolean numbers;
}
