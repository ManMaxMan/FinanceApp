package com.github.ManMaxMan.FinanceApp.serviceAudit.service.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.PageAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.UserAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAuditService {
    AuditEntity get (UUID uuid);
    PageAuditDTO getPage(Pageable pageable);
}
