package com.github.ManMaxMan.FinanceApp.serviceAudit.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditDaoService;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditInternalService;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IConverterToEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuditInternalServiceImpl implements IAuditInternalService {

    private final IAuditDaoService auditDaoService;
    private final IConverterToEntity converterToEntity;

    private final static Logger logger = LogManager.getLogger();

    public AuditInternalServiceImpl(IAuditDaoService auditDaoService, IConverterToEntity converterToEntity) {
        this.auditDaoService = auditDaoService;
        this.converterToEntity = converterToEntity;
    }

    @Override
    @Transactional
    public void create(AuditCreateDTO auditCreateDTO) {

        AuditEntity auditEntity = converterToEntity.convert(auditCreateDTO);
        auditEntity.setUuid(UUID.randomUUID());
        auditEntity.setDtCreate(LocalDateTime.now());

        auditDaoService.create(auditEntity);

        logger.log(Level.INFO, "Create new audit action");

    }
}
