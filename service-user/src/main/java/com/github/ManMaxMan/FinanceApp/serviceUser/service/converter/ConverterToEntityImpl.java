package com.github.ManMaxMan.FinanceApp.serviceUser.service.converter;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IConverterToEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ConverterToEntityImpl implements IConverterToEntity {

    private final PasswordEncoder encoder;

    public ConverterToEntityImpl(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserEntity convert(UserRegistrationDTO item) {

        UserEntity userEntity = new UserEntity();
        userEntity.setMail(item.getMail());
        userEntity.setFio(item.getFio());
        userEntity.setPassword(encoder.encode(item.getPassword()));

        return userEntity;
    }
}
