package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserEntityDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UpdateDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserAddUpdateDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.VerificationEntity;

import java.util.UUID;

public interface IManagementService {
    void addUser (UserAddUpdateDTO userAddDTO);
    UserEntity getUser (UUID uuid);
    void updateUser (UpdateDTO updateDTO);
    VerificationEntity generateVerificationEntity ();
    PageOfUserEntityDTO generate (PageOfUserEntityDTO pageOfUserEntityDTO);
}
