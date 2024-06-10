package com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.api;


import com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(value = "userClientFeign", url = "http://localhost:8080/users/{uuid}")
public interface UserClientFeign {

    @GetMapping
    UserDTO getUserByUuid(@RequestHeader("Authorization") String bearerToken,
                          @PathVariable("uuid") UUID uuid);

}
