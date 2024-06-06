package com.github.ManMaxMan.FinanceApp.serviceUser.core.dto;

import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import lombok.*;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PageOfUserEntityDTO {
    private Integer number;

    private Integer size;

    private Integer totalPages;

    private Long totalElements;

    private Boolean first;

    private Integer numberOfElements;

    private Boolean last;

    private List<UserEntity> content;
}
