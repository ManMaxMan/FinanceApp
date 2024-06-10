package com.github.ManMaxMan.FinanceApp.serviceClassifier;


import com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.config.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties({JWTProperty.class})
@EnableFeignClients
public class ServiceClassifier {
    public static void main(String[] args) {
        SpringApplication.run(ServiceClassifier.class, args);
    }
}
