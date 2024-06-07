package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.api.ICategoryRepository;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CategoryDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.PageOfCategoryEntityDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICategoryDaoService;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICategoryService;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.IConverterToEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDaoService categoryDaoService;
    private final IConverterToEntity converterToEntity;

    private final static Logger logger = LogManager.getLogger();

    public CategoryServiceImpl(ICategoryDaoService categoryDaoService, IConverterToEntity converterToEntity) {
        this.categoryDaoService = categoryDaoService;
        this.converterToEntity = converterToEntity;
    }

    @Override
    @Transactional
    public void create(CategoryDTO categoryDTO) {

        if (categoryDTO.getTitle()==null){
            throw new IllegalArgumentException("Title category is empty");
        }

        if (categoryDaoService.isExist(categoryDTO.getTitle())){
            throw new IllegalArgumentException("Category is exist");
        }

        CategoryEntity category = converterToEntity.convert(categoryDTO);
        category.setUuid(UUID.randomUUID());
        LocalDateTime dateTime = LocalDateTime.now();
        category.setDtCreate(dateTime);
        category.setDtUpdate(dateTime);

        categoryDaoService.create(category);

        logger.log(Level.INFO, "Category create successful");
    }

    @Override
    @Transactional
    public PageOfCategoryEntityDTO getPage(PageOfCategoryEntityDTO item) {

        Page<CategoryEntity> pageCategory = categoryDaoService.getPage(
                item.getNumber(), item.getSize());

        item.setFirst(pageCategory.isFirst());
        item.setLast(pageCategory.isLast());
        item.setTotalPages(pageCategory.getTotalPages());
        item.setTotalElements(pageCategory.getTotalElements());
        item.setNumberOfElements(pageCategory.getNumberOfElements());
        item.setContent(pageCategory.getContent());

        logger.log(Level.INFO, "Category page get successful");

        return item;
    }

}
