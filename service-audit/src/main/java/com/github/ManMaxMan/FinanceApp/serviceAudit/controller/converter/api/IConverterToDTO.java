package com.github.ManMaxMan.FinanceApp.serviceAudit.controller.converter.api;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.PageAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import org.springframework.data.domain.Page;

public interface IConverterToDTO {
    PageAuditDTO convert (Page<AuditEntity> pageEntities);
    AuditDTO convert (AuditEntity auditEntity);
}
