package com.github.ManMaxMan.FinanceApp.serviceAudit.controller.http;

import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.PageAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final IAuditService auditService;
    private final IConverterToDTO converterToDTO;

    public AuditController(IAuditService auditService, IConverterToDTO converterToDTO) {
        this.auditService = auditService;
        this.converterToDTO = converterToDTO;
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<PageAuditDTO> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        PageAuditDTO pageAuditDTO = auditService.getPage(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(pageAuditDTO);

    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public ResponseEntity<AuditDTO> getByUuid(@PathVariable(value = "uuid") UUID uuid) {

        AuditEntity entity = auditService.get(uuid);

        AuditDTO auditDTO = converterToDTO.convert(entity);


        return ResponseEntity.status(HttpStatus.OK).body(auditDTO);
    }
}
