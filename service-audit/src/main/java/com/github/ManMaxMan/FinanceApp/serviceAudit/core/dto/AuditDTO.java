package com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.config.CustomLocalDateTimeDesSerializer;
import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.config.CustomLocalDateTimeSerializer;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.enums.ETypeEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuditDTO {

    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime dtCreate;

    private UserAuditDTO user;

    private String text;

    private ETypeEntity type;

    private UUID id;

}
