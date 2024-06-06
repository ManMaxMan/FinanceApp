package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl;


import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.api.IUsersRepository;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        return userRepository.saveAndFlush(userEntity);
    }

    @Override
    public List<UserEntity> getByUserStatusAndMessageStatus(EUserStatus userStatus,
                                                            EMessageStatus messageStatus) {
        return userRepository.findByStatusAndVerificationEntity_MessageStatus(userStatus, messageStatus);
    }

    @Override
    @Transactional
    public Optional<UserEntity> getByMail(String mail) {
        return userRepository.findByMail(mail);
    }

}
