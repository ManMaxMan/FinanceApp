package com.github.ManMaxMan.FinanceApp.serviceUser.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
@Getter
@Setter
public class JWTProperty {
    private String secret;
    private String issuer;
}
