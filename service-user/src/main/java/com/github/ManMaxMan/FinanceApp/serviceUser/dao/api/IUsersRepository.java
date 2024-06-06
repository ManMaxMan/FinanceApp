package com.github.ManMaxMan.FinanceApp.serviceUser.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EUserStatus;
import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUsersRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByStatusAndVerificationEntity_MessageStatus (EUserStatus status, EMessageStatus messageStatus);
    Optional<UserEntity> findByMail(String mail);
}
