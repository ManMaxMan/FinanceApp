package com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.http;


import com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.*;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classifier/currency")
public class CurrencyController {
    private final ICurrencyService currencyService;
    private final IConverterToDTO converterToDTO;

    public CurrencyController(ICurrencyService currencyService, IConverterToDTO converterToDTO) {
        this.currencyService = currencyService;
        this.converterToDTO = converterToDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCurrency(@RequestBody CurrencyDTO currencyDTO) {
        currencyService.create(currencyDTO);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PageOfCurrencyDTO> getPageCurrency(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "20") Integer size) {
        PageOfCurrencyEntityDTO pageOfCurrencyEntityDTO = new PageOfCurrencyEntityDTO();
        pageOfCurrencyEntityDTO.setNumber(page);
        pageOfCurrencyEntityDTO.setSize(size);

        PageOfCurrencyDTO pageOfCurrencyDTO= converterToDTO.convert(currencyService.getPage(pageOfCurrencyEntityDTO));

        return ResponseEntity.status(HttpStatus.OK).body(pageOfCurrencyDTO);
    }

}
