package com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.config.CustomLocalDateTimeDesSerializer;
import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.config.CustomLocalDateTimeSerializer;
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
public class OperationDTO {

    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime dtCreate;

    @JsonProperty("dt_update")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime dtUpdate;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime date;

    private String description;

    private UUID category;

    private Double value;

    private UUID currency;

}
