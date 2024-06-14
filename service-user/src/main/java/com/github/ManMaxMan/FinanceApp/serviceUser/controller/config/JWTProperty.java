package com.github.ManMaxMan.FinanceApp.serviceUser.controller.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
@Getter
@Setter
public class JWTProperty {
    private String secret;
    private String issuer;
}
