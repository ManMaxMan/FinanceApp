package com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto;

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
public class UpdateAccountDTO {

    private UUID uuid;
    private LocalDateTime dtUpdate;
    private AccountCreateDTO accountCreateDTO;

}
