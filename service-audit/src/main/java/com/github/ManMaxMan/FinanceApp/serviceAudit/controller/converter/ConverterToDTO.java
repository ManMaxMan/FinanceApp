package com.github.ManMaxMan.FinanceApp.serviceAudit.controller.converter;

import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.PageAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ConverterToDTO implements IConverterToDTO {

    @Override
    public PageAuditDTO convert(Page<AuditEntity> pageEntities) {
        return PageAuditDTO.builder()
                .number(pageEntities.getNumber())
                .size(pageEntities.getSize())
                .totalPages(pageEntities.getTotalPages())
                .totalElements(pageEntities.getTotalElements())
                .first(pageEntities.isFirst())
                .numberOfElements(pageEntities.getNumberOfElements())
                .last(pageEntities.isLast())
                .content(pageEntities.getContent().stream().map(this::convert).toList())
                .build();
    }

    @Override
    public AuditDTO convert (AuditEntity auditEntity) {
        return AuditDTO.builder()
                .uuid(auditEntity.getUuid())
                .dtCreate(auditEntity.getDtCreate())
                .text(auditEntity.getText())
                .type(auditEntity.getTypeEntity())
                .id(auditEntity.getUuidEntity())
                .build();
    }
}
