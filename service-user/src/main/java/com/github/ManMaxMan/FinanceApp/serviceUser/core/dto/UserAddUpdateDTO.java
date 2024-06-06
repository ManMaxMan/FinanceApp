package com.github.ManMaxMan.FinanceApp.serviceUser.core.dto;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserAddUpdateDTO {

    private String mail;

    private String fio;

    private EUserRole role;

    private EUserStatus status;

    private String password;

}
