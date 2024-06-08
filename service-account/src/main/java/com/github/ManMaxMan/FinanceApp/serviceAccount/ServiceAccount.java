package com.github.ManMaxMan.FinanceApp.serviceAccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ServiceAccount {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAccount.class, args);
    }
}
