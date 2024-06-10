package com.github.ManMaxMan.FinanceApp.serviceAccount;

import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.config.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableFeignClients
@EnableConfigurationProperties({JWTProperty.class})
public class ServiceAccount {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAccount.class, args);
    }
}
