package com.github.ManMaxMan.FinanceApp.serviceAudit.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditCreateDTO;

public interface IAuditInternalService {
    void create (AuditCreateDTO auditCreateDTO);
}
