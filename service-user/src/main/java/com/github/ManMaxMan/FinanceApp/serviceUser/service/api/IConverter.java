package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;

public interface IConverter {
    UserEntity convert(UserDTO item);
    UserEntity convert(UserRegistrationDTO item);
}
