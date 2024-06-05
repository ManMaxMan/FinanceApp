package com.github.ManMaxMan.FinanceApp.serviceUser.service.converter;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IConverterToEntity;
import org.springframework.stereotype.Component;

@Component
public class ConverterToEntityImpl implements IConverterToEntity {
    @Override
    public UserEntity convert(UserDTO item) {
        return null;
    }

    @Override
    public UserEntity convert(UserRegistrationDTO item) {

        UserEntity userEntity = new UserEntity();
        userEntity.setMail(item.getMail());
        userEntity.setFio(item.getFio());
        userEntity.setPassword(item.getPassword());

        return userEntity;
    }
}
