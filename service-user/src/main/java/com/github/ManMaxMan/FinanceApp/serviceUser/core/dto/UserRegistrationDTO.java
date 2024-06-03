package com.github.ManMaxMan.FinanceApp.serviceUser.core.dto;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserRegistrationDTO {

    private String mail;

    private String fio;

    private String password;

}
