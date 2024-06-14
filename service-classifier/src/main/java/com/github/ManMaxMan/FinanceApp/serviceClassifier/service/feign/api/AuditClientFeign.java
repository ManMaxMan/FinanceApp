package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.feign.api;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.feign.dto.AuditCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auditClientFeign", url = "${urlAuditClientFeign}")
public interface AuditClientFeign {

    @PostMapping(produces = "application/json")
    void createAuditAction(@RequestBody AuditCreateDTO auditCreateDTO);
}
