package com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.converter.api;


import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.*;

public interface IConverterToDTO {
    CurrencyDTO convert(CurrencyEntity item);
    CategoryDTO convert(CategoryEntity item);
    PageOfCurrencyDTO convert(PageOfCurrencyEntityDTO pageOfCurrencyEntityDTO);
    PageOfCategoryDTO convert(PageOfCategoryEntityDTO pageOfCategoryEntityDTO);


}
