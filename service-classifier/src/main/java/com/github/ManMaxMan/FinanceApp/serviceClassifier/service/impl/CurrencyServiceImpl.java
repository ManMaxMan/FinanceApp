package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.api.ICurrencyRepository;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CurrencyDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.PageOfCurrencyEntityDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.IConverterToEntity;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICurrencyDaoService;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICurrencyService;
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
public class CurrencyServiceImpl implements ICurrencyService {

    private final ICurrencyDaoService currencyDaoService;
    private final IConverterToEntity converterToEntity;
    private final AuditClientFeign auditClientFeign;
    private final UserHolder userHolder;

    private final static Logger logger = LogManager.getLogger();

    public CurrencyServiceImpl(ICurrencyDaoService currencyDaoService, IConverterToEntity converterToEntity, AuditClientFeign auditClientFeign, UserHolder userHolder) {
        this.currencyDaoService = currencyDaoService;
        this.converterToEntity = converterToEntity;
        this.auditClientFeign = auditClientFeign;
        this.userHolder = userHolder;
    }

    @Override
    @Transactional
    public void create(CurrencyDTO currencyDTO) {

        if (currencyDTO.getTitle()==null||currencyDTO.getDescription()==null){
            throw new IllegalArgumentException("Some field currency is empty");
        }

        if (currencyDaoService.isExist(currencyDTO.getTitle())){
            throw new IllegalArgumentException("Currency is exist");
        }

        CurrencyEntity currency = converterToEntity.convert(currencyDTO);
        currency.setUuid(UUID.randomUUID());
        LocalDateTime dateTime = LocalDateTime.now();
        currency.setDtCreate(dateTime);
        currency.setDtUpdate(dateTime);

        currencyDaoService.create(currency);

        AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                .type(ETypeEntity.CURRENCY)
                .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                .uuidEntity(currency.getUuid())
                .text("Create currency")
                .build();
        auditClientFeign.createAuditAction(userHolder.getUser().getPassword(),auditCreateDTO);


        logger.log(Level.INFO, "Currency create successful");
    }

    @Override
    @Transactional
    public PageOfCurrencyEntityDTO getPage(PageOfCurrencyEntityDTO pageOfCurrencyEntityDTO) {

        Page<CurrencyEntity> page = currencyDaoService.getPage(
                pageOfCurrencyEntityDTO.getNumber(), pageOfCurrencyEntityDTO.getSize());

        pageOfCurrencyEntityDTO.setFirst(page.isFirst());
        pageOfCurrencyEntityDTO.setLast(page.isLast());
        pageOfCurrencyEntityDTO.setTotalPages(page.getTotalPages());
        pageOfCurrencyEntityDTO.setTotalElements(page.getTotalElements());
        pageOfCurrencyEntityDTO.setNumberOfElements(page.getNumberOfElements());
        pageOfCurrencyEntityDTO.setContent(page.getContent());

        pageOfCurrencyEntityDTO.getContent().forEach(entity->{
            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.REPORT)
                    .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                    .uuidEntity(entity.getUuid())
                    .text("Get information about currency in page")
                    .build();
            auditClientFeign.createAuditAction(userHolder.getUser().getPassword(),auditCreateDTO);
        });

        logger.log(Level.INFO, "Currency page get successful");

        return pageOfCurrencyEntityDTO;
    }

    @Override
    public Boolean existByUuid(UUID uuid) {
        return currencyDaoService.isExist(uuid);
    }
}
