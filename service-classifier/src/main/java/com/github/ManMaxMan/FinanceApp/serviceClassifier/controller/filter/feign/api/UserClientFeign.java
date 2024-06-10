package com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.filter.feign.api;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.filter.feign.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "userClient", url = "http://localhost:8080/cabinet/me")
public interface UserClientFeign {

    @GetMapping(produces = "application/json")
    UserDTO getMe(@RequestHeader("Authorization") String bearerToken);
}
