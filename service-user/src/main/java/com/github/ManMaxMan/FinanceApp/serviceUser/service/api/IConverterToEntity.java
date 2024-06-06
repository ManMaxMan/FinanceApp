package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;

public interface IConverterToEntity {
    UserEntity convert(UserRegistrationDTO item);
}
