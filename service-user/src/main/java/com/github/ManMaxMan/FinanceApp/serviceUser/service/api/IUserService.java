package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import java.util.List;
import java.util.UUID;

public interface IUserService {

    UserEntity create(UserEntity userEntity);
    List<UserEntity> getByStatus(EUserStatus userStatus);
    UserEntity getByUUID(UUID uuid);

}
