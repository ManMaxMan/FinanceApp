package com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "auditClient", url = "http://localhost:8081/audit")
public interface AuditClientFeign {

    @PostMapping(produces = "application/json")
    void createAuditAction(@RequestHeader("Authorization") String bearerToken,
                           @RequestBody AuditCreateDTO auditCreateDTO);
}
