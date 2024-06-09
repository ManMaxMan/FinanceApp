package com.github.ManMaxMan.FinanceApp.serviceAudit.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.UserAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditDaoService;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuditServiceImpl implements IAuditService {

    private final IAuditDaoService auditDaoService;

    private final static Logger logger = LogManager.getLogger();


    public AuditServiceImpl(IAuditDaoService auditDaoService) {
        this.auditDaoService = auditDaoService;
    }

    @Override
    public AuditEntity get(UUID uuid) {

        Optional<AuditEntity> optional = auditDaoService.getByUuid(uuid);
        if (optional.isPresent()) {

            logger.log(Level.INFO, "Get audit action by uuid: "+ uuid);

            return optional.get();

        }else {
            throw new IllegalArgumentException("Uuid action not exist");
        }
    }

    @Override
    public UserAuditDTO user(UUID userUuid) {
        return null;
    }
}
