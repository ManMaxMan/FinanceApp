package com.github.ManMaxMan.FinanceApp.serviceAccount;

import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.config.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication  (exclude = { SecurityAutoConfiguration.class , UserDetailsServiceAutoConfiguration.class})
@EnableWebMvc
@EnableFeignClients (basePackages = {"com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api","com.github.ManMaxMan.FinanceApp.serviceAccount.controller.filter.feign.api"})
@EnableConfigurationProperties({JWTProperty.class})
@ImportAutoConfiguration({FeignAutoConfiguration.class, HttpClientConfiguration.class})
public class ServiceAccount {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAccount.class, args);
    }
}
