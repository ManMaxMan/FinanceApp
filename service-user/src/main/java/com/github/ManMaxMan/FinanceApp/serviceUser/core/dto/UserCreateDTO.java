package com.github.ManMaxMan.FinanceApp.serviceUser.core.dto;

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
public class UserCreateDTO {

    private UUID uuid;

    private LocalDateTime dtCreate;

    private LocalDateTime dtUpdate;

    private String mail;

    private String fio;

    private EUserRole role;

    private EUserStatus status;

    private String password;

}
