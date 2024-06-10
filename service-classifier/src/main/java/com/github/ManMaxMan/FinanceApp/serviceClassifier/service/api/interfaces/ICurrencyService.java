package com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.CurrencyDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.PageOfCurrencyEntityDTO;

import java.util.UUID;

public interface ICurrencyService {
    void create (CurrencyDTO currencyDTO);
    PageOfCurrencyEntityDTO getPage (PageOfCurrencyEntityDTO item);
    Boolean existByUuid (UUID uuid);
}
