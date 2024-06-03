package com.github.ManMaxMan.FinanceApp.serviceUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories
@EnableScheduling
public class ServiceUser {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUser.class, args);
    }
}
