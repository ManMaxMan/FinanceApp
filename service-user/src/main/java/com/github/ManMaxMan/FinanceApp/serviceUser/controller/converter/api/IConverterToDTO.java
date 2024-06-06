package com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserEntityDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;

public interface IConverterToDTO {
    UserDTO convert(UserEntity item);
    PageOfUserDTO convertPage(PageOfUserEntityDTO pageOfUserEntityDTO);
}
