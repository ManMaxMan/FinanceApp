package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CategoryDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.PageOfCategoryEntityDTO;

import java.util.UUID;

public interface ICategoryService {
    void create (CategoryDTO categoryDTO);
    PageOfCategoryEntityDTO getPage (PageOfCategoryEntityDTO item);
    Boolean existsByUuid (UUID uuid);
}
