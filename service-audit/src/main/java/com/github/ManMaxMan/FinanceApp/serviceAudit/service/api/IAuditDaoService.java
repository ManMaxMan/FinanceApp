package com.github.ManMaxMan.FinanceApp.serviceAudit.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IAuditDaoService {
    Optional<AuditEntity> getByUuid (UUID uuid);
    void create (AuditEntity auditEntity);
    Page<AuditEntity> getPage(Pageable pageable);
}
