package com.github.ManMaxMan.FinanceApp.serviceClassifier;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ServiceClassifier {
    public static void main(String[] args) {
        SpringApplication.run(ServiceClassifier.class, args);
    }
}
