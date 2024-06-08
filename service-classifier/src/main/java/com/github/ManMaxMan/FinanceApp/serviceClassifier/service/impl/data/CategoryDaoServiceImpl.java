package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.impl.data;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.api.ICategoryRepository;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICategoryDaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryDaoServiceImpl implements ICategoryDaoService {

    private final ICategoryRepository categoryRepository;

    public CategoryDaoServiceImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Boolean isExist(String name) {
        return categoryRepository.existsByTitle(name);
    }

    @Override
    @Transactional
    public void create(CategoryEntity category) {
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public Page<CategoryEntity> getPage(Integer page, Integer size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
    }
}
