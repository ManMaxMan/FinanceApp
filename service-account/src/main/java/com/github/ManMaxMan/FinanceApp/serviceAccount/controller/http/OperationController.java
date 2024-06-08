package com.github.ManMaxMan.FinanceApp.serviceAccount.controller.http;


import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.OperationCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.PageOperationDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account/{uuid}/operation")
public class OperationController {

    private final IOperationService operationService;
    private final IConverterToDTO converterToDTO;

    public OperationController(IOperationService operationService, IConverterToDTO converterToDTO) {
        this.operationService = operationService;
        this.converterToDTO = converterToDTO;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable(value = "uuid") UUID uuid,
                       @RequestBody OperationCreateDTO operation) {
        operationService.create(uuid, operation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageOperationDTO> getPage (@PathVariable(value = "uuid") UUID uuid,
                                                     @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<OperationEntity> pageEntities = operationService.getPage(uuid, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(converterToDTO.convert(pageEntities));

    }
}
