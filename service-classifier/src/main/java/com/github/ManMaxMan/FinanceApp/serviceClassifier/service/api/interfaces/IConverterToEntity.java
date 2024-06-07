package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CategoryDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CurrencyDTO;

public interface IConverterToEntity {
    CurrencyEntity convert (CurrencyDTO currencyDTO);
    CategoryEntity convert (CategoryDTO categoryDTO);
}
