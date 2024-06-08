package com.github.ManMaxMan.FinanceApp.serviceUser.service.impl.data;


import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.api.IUsersRepository;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public void create(UserEntity userEntity) {
        userRepository.saveAndFlush(userEntity);
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

    @Override
    public Optional<UserEntity> getByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Override
    public Page<UserEntity> getAll(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Boolean isExist(String mail) {
        return userRepository.existsByMail(mail);
    }


}
