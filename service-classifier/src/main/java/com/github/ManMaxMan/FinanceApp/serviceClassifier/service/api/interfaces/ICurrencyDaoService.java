package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CurrencyDTO;
import org.springframework.data.domain.Page;

public interface ICurrencyDaoService {
    Boolean isExist (String name);
    void create (CurrencyEntity currency);
    Page<CurrencyEntity> getPage (Integer page, Integer size);
}
