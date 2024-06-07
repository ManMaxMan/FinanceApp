package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.converter;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CategoryDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CurrencyDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.IConverterToEntity;
import org.springframework.stereotype.Component;

@Component
public class ConverterToEntity implements IConverterToEntity {
    @Override
    public CurrencyEntity convert(CurrencyDTO currencyDTO) {

        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setTitle(currencyEntity.getTitle());
        currencyEntity.setDescription(currencyEntity.getDescription());

        return currencyEntity;
    }

    @Override
    public CategoryEntity convert(CategoryDTO categoryDTO) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle(categoryEntity.getTitle());

        return categoryEntity;
    }
}
