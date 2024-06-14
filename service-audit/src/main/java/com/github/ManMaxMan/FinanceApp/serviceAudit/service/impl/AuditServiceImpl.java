package com.github.ManMaxMan.FinanceApp.serviceAudit.service.impl;

import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.utils.JwtTokenHandler;
import com.github.ManMaxMan.FinanceApp.serviceAudit.controller.utils.UserDetailsImpl;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.AuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.PageAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.dto.UserAuditDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.enums.ETypeEntity;
import com.github.ManMaxMan.FinanceApp.serviceAudit.core.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity.AuditEntity;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditDaoService;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.api.IAuditService;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.api.AuditClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.api.UserClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.feign.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceAudit.service.utils.UserHolder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuditServiceImpl implements IAuditService {

    private final IAuditDaoService auditDaoService;
    private final AuditClientFeign auditClientFeign;
    private final UserHolder userHolder;
    private final JwtTokenHandler tokenHandler;
    private final UserClientFeign userClientFeign;

    private final static Logger logger = LogManager.getLogger();


    public AuditServiceImpl(IAuditDaoService auditDaoService, AuditClientFeign auditClientFeign, UserHolder userHolder, JwtTokenHandler tokenHandler, UserClientFeign userClientFeign) {
        this.auditDaoService = auditDaoService;
        this.auditClientFeign = auditClientFeign;
        this.userHolder = userHolder;
        this.tokenHandler = tokenHandler;
        this.userClientFeign = userClientFeign;
    }

    @Override
    @Transactional
    public AuditEntity get(UUID uuid) {

        Optional<AuditEntity> optional = auditDaoService.getByUuid(uuid);
        if (optional.isPresent()) {

            System.out.println("in get "+UUID.fromString(userHolder.getUser().getUsername()));

            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.AUDIT)
                    .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                    .uuidEntity(uuid)
                    .text("Get information about the audit record")
                    .build();
            auditClientFeign.createAuditAction(auditCreateDTO);

            logger.log(Level.INFO, "Get audit action by uuid: "+ uuid);

            return optional.get();

        }else {
            throw new IllegalArgumentException("Uuid action not exist");
        }
    }

    @Override
    @Transactional
    public PageAuditDTO getPage(Pageable pageable) {
        Page<AuditEntity> pageEntities = auditDaoService.getPage(pageable);

        PageAuditDTO pageAuditDTO = PageAuditDTO.builder()
                .first(pageEntities.isFirst())
                .last(pageEntities.isLast())
                .totalPages(pageEntities.getTotalPages())
                .totalElements(pageEntities.getTotalElements())
                .size(pageEntities.getSize())
                .number(pageEntities.getNumber())
                .numberOfElements(pageEntities.getNumberOfElements())
                .content(new ArrayList<>())
                .build();

        UserDetailsImpl userDetails = (UserDetailsImpl) userHolder.getUser();
        String newToken = tokenHandler.generateAccessToken(userDetails.getMail(), EUserRole.SYSTEM);

        pageEntities.getContent().forEach(entity->{

            UserDTO userDTO = userClientFeign.get("Bearer "+newToken,
                    entity.getUserUuid()).getBody();
            UserAuditDTO userAuditDTO = UserAuditDTO.builder()
                    .fio(userDTO.getFio())
                    .mail(userDTO.getMail())
                    .role(userDTO.getRole())
                    .uuid(userDTO.getUuid())
                    .build();

            AuditDTO auditDTO = AuditDTO.builder()
                    .id(entity.getUuidEntity())
                    .type(entity.getTypeEntity())
                    .text(entity.getText())
                    .uuid(entity.getUuid())
                    .dtCreate(entity.getDtCreate())
                    .user(userAuditDTO)
                    .build();

            pageAuditDTO.getContent().add(auditDTO);

            AuditCreateDTO auditCreateDTO = AuditCreateDTO.builder()
                    .type(ETypeEntity.REPORT)
                    .uuidUser(UUID.fromString(userHolder.getUser().getUsername()))
                    .uuidEntity(entity.getUuid())
                    .text("Get information about the audit record in page")
                    .build();
            auditClientFeign.createAuditAction(auditCreateDTO);
        });

        logger.log(Level.INFO, "Get audit page");

        return pageAuditDTO;
    }
}
