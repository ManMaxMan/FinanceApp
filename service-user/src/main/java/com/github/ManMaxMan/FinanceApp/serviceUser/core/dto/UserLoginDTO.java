package com.github.ManMaxMan.FinanceApp.serviceUser.core.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserLoginDTO {

    private String mail;

    private String password;
}
