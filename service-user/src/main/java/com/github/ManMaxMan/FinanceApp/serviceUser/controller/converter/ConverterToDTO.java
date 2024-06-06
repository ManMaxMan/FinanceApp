package com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter;

import com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserEntityDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ConverterToDTO implements IConverterToDTO {
    @Override
    public UserDTO convert(UserEntity item) {
        return UserDTO.builder()
                .uuid(item.getUuid())
                .mail(item.getMail())
                .dtCreate(item.getDtCreate())
                .dtUpdate(item.getDtUpdate())
                .fio(item.getFio())
                .status(item.getStatus())
                .role(item.getRole())
                .build();
    }

    @Override
    public PageOfUserDTO convertPage(PageOfUserEntityDTO pageOfUserEntityDTO) {

        return PageOfUserDTO.builder()
                .first(pageOfUserEntityDTO.getFirst())
                .last(pageOfUserEntityDTO.getLast())
                .size(pageOfUserEntityDTO.getSize())
                .numberOfElements(pageOfUserEntityDTO.getNumberOfElements())
                .number(pageOfUserEntityDTO.getNumber())
                .totalElements(pageOfUserEntityDTO.getTotalElements())
                .totalPages(pageOfUserEntityDTO.getTotalPages())
                .content(pageOfUserEntityDTO.getContent().stream().map(this::convert).toList())
                .build();

    }
}
