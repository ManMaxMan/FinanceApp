package com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.converter;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.*;
import org.springframework.stereotype.Component;

@Component
public class ConvertToDTO implements IConverterToDTO {

    @Override
    public CurrencyDTO convert(CurrencyEntity item) {
        return CurrencyDTO.builder()
                .uuid(item.getUuid())
                .title(item.getTitle())
                .description(item.getDescription())
                .dtCreate(item.getDtCreate())
                .dtUpdate(item.getDtUpdate())
                .build();
    }

    @Override
    public CategoryDTO convert(CategoryEntity item) {
        return CategoryDTO.builder()
                .uuid(item.getUuid())
                .title(item.getTitle())
                .dtCreate(item.getDtCreate())
                .dtUpdate(item.getDtUpdate())
                .build();
    }

    @Override
    public PageOfCurrencyDTO convert(PageOfCurrencyEntityDTO item) {
        return PageOfCurrencyDTO.builder()
                .first(item.getFirst())
                .last(item.getLast())
                .size(item.getSize())
                .numberOfElements(item.getNumberOfElements())
                .number(item.getNumber())
                .totalElements(item.getTotalElements())
                .totalPages(item.getTotalPages())
                .content(item.getContent().stream().map(this::convert).toList())
                .build();
    }

    @Override
    public PageOfCategoryDTO convert(PageOfCategoryEntityDTO item) {
        return PageOfCategoryDTO.builder()
                .first(item.getFirst())
                .last(item.getLast())
                .size(item.getSize())
                .numberOfElements(item.getNumberOfElements())
                .number(item.getNumber())
                .totalElements(item.getTotalElements())
                .totalPages(item.getTotalPages())
                .content(item.getContent().stream().map(this::convert).toList())
                .build();
    }
}
