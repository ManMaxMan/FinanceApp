package com.github.ManMaxMan.FinanceApp.serviceUser.controller.http;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ManMaxMan.FinanceApp.serviceUser.controller.config.CustomLocalDateTimeDesSerializer;
import com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter.ConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.*;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IManagementService;
import jakarta.persistence.Convert;
import org.springframework.data.convert.ValueConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IManagementService managementService;
    private final ConverterToDTO converterToDTO;

    public UserController(IManagementService managementService, ConverterToDTO converterToDTO) {
        this.managementService = managementService;
        this.converterToDTO = converterToDTO;
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserAddUpdateDTO userCreateDTO) {
        managementService.addUser(userCreateDTO);
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("uuid") UUID uuid) {
        UserDTO userDTO = converterToDTO.convert(managementService.getUser(uuid));
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<PageOfUserDTO> getPageUsers
            (@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        PageOfUserEntityDTO pageOfUserEntity = new PageOfUserEntityDTO();
        pageOfUserEntity.setNumber(page);
        pageOfUserEntity.setSize(size);

        PageOfUserDTO pageOfUserDTO= converterToDTO.convertPage(managementService.generate(pageOfUserEntity));
        return ResponseEntity.status(HttpStatus.OK).body(pageOfUserDTO);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}", produces = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable("uuid") UUID uuid,
                                                       @PathVariable("dt_update") Long dtUpdate,
                                                       @RequestBody UserAddUpdateDTO userAddUpdateDTO) {

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dtUpdate), ZoneId.systemDefault());

        UpdateDTO updateDTO = UpdateDTO.builder()
                .uuid(uuid)
                .dtUpdate(dateTime)
                .userAddUpdateDTO(userAddUpdateDTO)
                .build();
        managementService.updateUser(updateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
