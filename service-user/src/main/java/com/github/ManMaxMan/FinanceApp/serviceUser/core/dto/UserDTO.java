package com.github.ManMaxMan.FinanceApp.serviceUser.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.ManMaxMan.FinanceApp.serviceUser.controller.config.CustomLocalDateTimeDesSerializer;
import com.github.ManMaxMan.FinanceApp.serviceUser.controller.config.CustomLocalDateTimeSerializer;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
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
