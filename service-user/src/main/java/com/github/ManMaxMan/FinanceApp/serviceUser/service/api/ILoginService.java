package com.github.ManMaxMan.FinanceApp.serviceUser.service.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.TokenDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserLoginDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;

public interface ILoginService {
    String login (UserLoginDTO userLoginDTO);
    UserEntity getUser (TokenDTO token);
}
