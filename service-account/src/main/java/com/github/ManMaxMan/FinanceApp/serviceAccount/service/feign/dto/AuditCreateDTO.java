package com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.dto;


import com.github.ManMaxMan.FinanceApp.serviceAccount.service.feign.enums.ETypeEntity;
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
