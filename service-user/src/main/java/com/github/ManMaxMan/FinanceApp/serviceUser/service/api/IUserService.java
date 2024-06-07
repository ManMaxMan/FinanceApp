package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    void create(UserEntity userEntity);
    List<UserEntity> getByUserStatusAndMessageStatus(EUserStatus userStatus, EMessageStatus messageStatus);
    Optional<UserEntity> getByMail(String mail);
    Optional<UserEntity> getByUuid (UUID uuid);
    Page<UserEntity> getAll (Integer page, Integer size);
    Boolean isExist (String mail);

}
