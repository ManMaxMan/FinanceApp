package com.github.ManMaxMan.FinanceApp.serviceAudit.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;

public interface IConverterToEntity {
    AuditEntity convert (AuditCreateDTO auditCreateDTO);
}
