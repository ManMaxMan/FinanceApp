package com.github.ManMaxMan.FinanceApp.serviceAccount.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAccountRepository extends JpaRepository<AccountEntity, UUID> {
    Page<AccountEntity> findAllByUserUuid(Pageable pageable, UUID userUuid);
    Optional<AccountEntity> findByUuid (UUID uuid);
    Boolean existsByUuid (UUID uuid);
}
