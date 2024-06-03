package com.github.ManMaxMan.FinanceApp.serviceUser.core.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VerificationDTO {
    private String email;
    private String verificationCode;
}
