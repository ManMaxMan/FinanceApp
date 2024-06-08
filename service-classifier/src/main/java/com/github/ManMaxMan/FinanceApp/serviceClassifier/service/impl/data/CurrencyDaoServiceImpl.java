package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.impl.data;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.api.ICurrencyRepository;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICurrencyDaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CurrencyDaoServiceImpl implements ICurrencyDaoService {

    private final ICurrencyRepository currencyRepository;

    public CurrencyDaoServiceImpl(ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    @Transactional
    public Boolean isExist(String name) {
        return currencyRepository.existsByTitle(name);
    }

    @Override
    @Transactional
    public void create(CurrencyEntity currency) {
        currencyRepository.saveAndFlush(currency);
    }

    @Override
    public Page<CurrencyEntity> getPage(Integer page, Integer size) {
        return currencyRepository.findAll(PageRequest.of(page, size));
    }
}
