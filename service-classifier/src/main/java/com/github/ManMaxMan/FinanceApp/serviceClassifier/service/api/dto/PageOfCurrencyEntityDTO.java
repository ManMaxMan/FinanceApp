package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PageOfCurrencyEntityDTO {

    private Integer number;

    private Integer size;

    private Integer totalPages;

    private Long totalElements;

    private Boolean first;

    private Integer numberOfElements;

    private Boolean last;

    private List<CurrencyEntity> content;

}
