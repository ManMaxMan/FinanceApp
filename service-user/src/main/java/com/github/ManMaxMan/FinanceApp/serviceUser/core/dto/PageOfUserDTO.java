package com.github.ManMaxMan.FinanceApp.serviceUser.core.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PageOfUserDTO {

    private Integer number;

    private Integer size;

    private Integer totalPages;

    private Integer totalElements;

    private Boolean first;

    private Integer numberOfElements;

    private Boolean last;

    private List<UserDTO> content;

}
