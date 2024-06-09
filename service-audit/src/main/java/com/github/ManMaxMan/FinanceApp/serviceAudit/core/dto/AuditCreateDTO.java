package com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.enums.ETypeEntity;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuditCreateDTO {

    private UUID uuidUser;
    private String text;
    private ETypeEntity type;
    private UUID uuidEntity;

}
