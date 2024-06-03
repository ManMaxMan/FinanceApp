package com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter;

import com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter.api.IConverter;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class Converter implements IConverter {
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
}
