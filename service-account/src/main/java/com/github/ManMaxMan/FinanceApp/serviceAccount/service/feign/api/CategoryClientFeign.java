package com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(value = "categoryClient", url = "http://localhost:8082/classifier/operation/category/{uuid}")
public interface CategoryClientFeign {

    @GetMapping
    Boolean isExistCategory(@RequestHeader("Authorization") String bearerToken,
                            @PathVariable("uuid") UUID uuid);

}
