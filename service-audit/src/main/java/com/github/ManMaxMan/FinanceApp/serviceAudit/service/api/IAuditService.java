package com.github.ManMaxMan.FinanceApp.serviceAudit.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.UserAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;

import java.util.UUID;

public interface IAuditService {
    AuditEntity get (UUID uuid);
    UserAuditDTO user (UUID userUuid);
}
