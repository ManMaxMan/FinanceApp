package com.github.ManMaxMan.FinanceApp.serviceAccount.controller.http;


import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.AccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.PageAccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.dto.UpdateAccountDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.service.api.IAccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;
    private final IConverterToDTO converterToDTO;

    public AccountController(IAccountService accountService, IConverterToDTO converterToDTO) {
        this.accountService = accountService;
        this.converterToDTO = converterToDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody AccountCreateDTO account) {
        this.accountService.create(account);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PageAccountDTO> getPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<AccountEntity> pageEntities = accountService.getPage(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(converterToDTO.convert(pageEntities));
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public ResponseEntity<AccountDTO> getByUuid(@PathVariable(value = "uuid") UUID uuid) {

        AccountEntity entity = accountService.getByUuid(uuid);

        return ResponseEntity.status(HttpStatus.OK).body(converterToDTO.convert(entity));
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") LocalDateTime dtUpdate,
                       @RequestBody AccountCreateDTO account) {

        UpdateAccountDTO updateAccount= UpdateAccountDTO.builder()
                .uuid(uuid)
                .dtUpdate(dtUpdate)
                .accountCreateDTO(account)
                .build();

        accountService.update(updateAccount);
    }
}
