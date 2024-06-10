package com.github.ManMaxMan.FinanceApp.serviceAudit.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.api.IAuditRepository;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditDaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuditDaoServiceImpl implements IAuditDaoService {

    private final IAuditRepository auditRepository;

    public AuditDaoServiceImpl(IAuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Optional<AuditEntity> getByUuid(UUID uuid) {
        return auditRepository.findByUuid(uuid);
    }

    @Override
    public void create(AuditEntity auditEntity) {
        auditRepository.saveAndFlush(auditEntity);
    }

    @Override
    public Page<AuditEntity> getPage(Pageable pageable) {
        return auditRepository.findAll(pageable);
    }
}
