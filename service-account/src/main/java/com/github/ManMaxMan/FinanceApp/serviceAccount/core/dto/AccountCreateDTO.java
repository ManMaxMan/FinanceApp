package com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto;

import com.github.ManMaxMan.FinanceApp.serviceAccount.core.enums.EAccountType;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AccountCreateDTO {

    private String title;
    private String description;
    private EAccountType type;
    private UUID currency;
}
