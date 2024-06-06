package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserEntity create(UserEntity userEntity);
    List<UserEntity> getByUserStatusAndMessageStatus(EUserStatus userStatus, EMessageStatus messageStatus);
    Optional<UserEntity> getByMail(String mail);

}
