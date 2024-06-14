package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.utils.UserDetailsImpl;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.api.ICategoryRepository;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CategoryDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.PageOfCategoryEntityDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICategoryDaoService;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICategoryService;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.IConverterToEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.feign.api.AuditClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.feign.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.feign.enums.ETypeEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.utils.UserHolder;
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
    private final AuditClientFeign auditClientFeign;
    private final UserHolder userHolder;

    private final static Logger logger = LogManager.getLogger();

    public CategoryServiceImpl(ICategoryDaoService categoryDaoService, IConverterToEntity converterToEntity, AuditClientFeign auditClientFeign, UserHolder userHolder) {
        this.categoryDaoService = categoryDaoService;
        this.converterToEntity = converterToEntity;
        this.auditClientFeign = auditClientFeign;
        this.userHolder = userHolder;
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

        UserDetailsImpl userDetails = (UserDetailsImpl) userHolder.getUser();

        AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                .type(ETypeEntity.CATEGORY)
                .uuidUser(UUID.fromString(userDetails.getUsername()))
                .uuidEntity(category.getUuid())
                .text("Create category")
                .build();
        auditClientFeign.createAuditAction(auditCreateDTO);

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

        /*item.getContent().forEach(entity->{
            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.REPORT)
                    .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                    .uuidEntity(entity.getUuid())
                    .text("Get information about category in page")
                    .build();
            auditClientFeign.createAuditAction(null,auditCreateDTO);
        });*/

        logger.log(Level.INFO, "Category page get successful");

        return item;
    }

    @Override
    public Boolean existsByUuid(UUID uuid) {
        return categoryDaoService.existsByUuid(uuid);
    }

}
