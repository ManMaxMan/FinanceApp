package com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PageOperationDTO {

    private Integer number;

    private Integer size;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_elements")
    private Long totalElements;

    private boolean first;

    @JsonProperty("number_of_elements")
    private Integer numberOfElements;

    private boolean last;

    private List<OperationDTO> content;
}
