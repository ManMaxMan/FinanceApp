package com.github.ManMaxMan.FinanceApp.serviceAudit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ServiceAudit {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAudit.class, args);
    }
}
