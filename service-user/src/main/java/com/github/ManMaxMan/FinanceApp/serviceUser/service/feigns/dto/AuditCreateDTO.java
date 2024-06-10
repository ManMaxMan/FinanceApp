package com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.dto;

import com.github.ManMaxMan.FinanceApp.serviceUser.service.feigns.enums.ETypeEntity;
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
