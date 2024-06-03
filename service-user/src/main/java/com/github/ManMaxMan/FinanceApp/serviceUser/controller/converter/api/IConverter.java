package com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;

public interface IConverter {
    UserDTO convert(UserEntity item);
}
