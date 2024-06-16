package com.github.ManMaxMan.FinanceApp.serviceAudit;

import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.config.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication  (exclude = { SecurityAutoConfiguration.class , UserDetailsServiceAutoConfiguration.class})
@EnableWebMvc
@EnableFeignClients (basePackages = {"com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.api"})
@EnableConfigurationProperties({JWTProperty.class})
//@ImportAutoConfiguration({FeignAutoConfiguration.class, HttpClientConfiguration.class})
public class ServiceAudit {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAudit.class, args);
    }
}
