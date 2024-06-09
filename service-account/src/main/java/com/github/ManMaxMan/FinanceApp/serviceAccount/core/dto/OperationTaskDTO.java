package com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.enums.EOperationTask;
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
public class OperationTaskDTO {

    private UUID accountUuid;

    private UUID operationUuid;

    private LocalDateTime dtUpdate;

    private OperationCreateDTO operationCreateDTO;

    private EOperationTask operationTask;
}
