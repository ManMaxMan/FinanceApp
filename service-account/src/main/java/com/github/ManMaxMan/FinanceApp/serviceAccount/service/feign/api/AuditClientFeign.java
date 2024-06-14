package com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.dto.AuditCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auditClientFeign")
public interface AuditClientFeign {

    @PostMapping(produces = "application/json")
    void createAuditAction(@RequestBody AuditCreateDTO auditCreateDTO);
}
