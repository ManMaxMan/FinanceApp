package com.github.ManMaxMan.FinanceApp.serviceAudit.service.converter;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IConverterToEntity;
import org.springframework.stereotype.Component;

@Component
public class ConverterToEntityImpl implements IConverterToEntity {
    @Override
    public AuditEntity convert(AuditCreateDTO auditCreateDTO) {

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setUserUuid(auditCreateDTO.getUuidUser());
        auditEntity.setTypeEntity(auditCreateDTO.getType());
        auditEntity.setUuidEntity(auditCreateDTO.getUuidEntity());
        auditEntity.setText(auditCreateDTO.getText());

        return auditEntity;
    }
}
