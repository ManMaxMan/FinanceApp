package com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto;

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
public class OperationCreateDTO {

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime date;

    private String description;

    private UUID category;

    private Double value;

    private UUID currency;
}
