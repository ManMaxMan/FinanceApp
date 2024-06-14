package com.github.ManMaxMan.FinanceApp.serviceAccount.controller.filter.feign.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.filter.feign.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "userClientFeign")
public interface UserClientFeign {

    @GetMapping(produces = "application/json")
    ResponseEntity<UserDTO> aboutUser(@RequestHeader("Authorization") String bearerToken);
}
