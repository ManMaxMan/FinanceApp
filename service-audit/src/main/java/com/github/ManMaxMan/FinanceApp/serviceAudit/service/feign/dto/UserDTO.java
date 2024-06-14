package com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.config.CustomLocalDateTimeDesSerializer;
import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.config.CustomLocalDateTimeSerializer;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.enums.EUserRole;
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
public class UserDTO {

    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime dtCreate;

    @JsonProperty("dt_update")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime dtUpdate;

    private String mail;

    private String fio;

    private EUserRole role;

    private EUserStatus status;

}
