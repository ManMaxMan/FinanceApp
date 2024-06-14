package com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(value = "classifierClientFeign", url = "${urlClassifierClientFeign}")
public interface ClassifierClientFeign {

    @GetMapping(value = "/operation/category/{uuid}")
    Boolean isExistCategory(@RequestHeader("Authorization") String bearerToken,
                            @PathVariable("uuid") UUID uuid);

    @GetMapping(value = "/currency/{uuid}")
    Boolean isExistCurrency(@RequestHeader("Authorization") String bearerToken,
                            @PathVariable("uuid") UUID uuid);

}
