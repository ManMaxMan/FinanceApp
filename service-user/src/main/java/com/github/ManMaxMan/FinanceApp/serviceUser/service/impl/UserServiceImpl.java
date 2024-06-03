package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;


import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.api.IUsersRepository;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

    private final IUsersRepository userRepository;

    public UserServiceImpl(IUsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserEntity create(UserEntity userEntity) {
        userEntity.setUuid(UUID.randomUUID());
        return userRepository.saveAndFlush(userEntity);
    }

    @Override
    public List<UserEntity> getByStatus(EUserStatus userStatus) {
        return userRepository.findByStatus(userStatus);
    }

    @Override
    @Transactional
    public UserEntity getByUUID(UUID uuid) {
        return null;
    }

}
