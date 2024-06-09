package com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.enums.EUserRole;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserAuditDTO {

    private UUID uuid;

    private String mail;

    private String fio;

    private EUserRole role;

}
