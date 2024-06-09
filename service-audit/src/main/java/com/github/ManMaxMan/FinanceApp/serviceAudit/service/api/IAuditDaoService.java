package com.github.ManMaxMan.FinanceApp.serviceAudit.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;

import java.util.Optional;
import java.util.UUID;

public interface IAuditDaoService {
    Optional<AuditEntity> getByUuid (UUID uuid);
    void create (AuditEntity auditEntity);
}
