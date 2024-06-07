package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import org.springframework.data.domain.Page;

public interface ICategoryDaoService {
    Boolean isExist (String name);
    void create (CategoryEntity category);
    Page<CategoryEntity> getPage (Integer page, Integer size);
}
