package com.github.ManMaxMan.FinanceApp.serviceAudit.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAuditRepository extends JpaRepository<AuditEntity, UUID> {
    Optional<AuditEntity> findByUuid (UUID uuid);
}
