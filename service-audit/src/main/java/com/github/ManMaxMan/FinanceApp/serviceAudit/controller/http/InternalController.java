package com.github.ManMaxMan.FinanceApp.serviceAudit.controller.http;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditInternalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit")
public class InternalController {

    private final IAuditInternalService auditInternalService;

    public InternalController(IAuditInternalService auditInternalService) {
        this.auditInternalService = auditInternalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody AuditCreateDTO auditCreateDTO) {

        auditInternalService.create(auditCreateDTO);
    }
}
