package com.github.ManMaxMan.FinanceApp.serviceAccount.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.AccountEntity;
import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IOperationRepository extends JpaRepository<OperationEntity, UUID> {
    Page<OperationEntity> findAllByAccountUuid(Pageable pageable, AccountEntity account);
    Boolean existsByUuid (UUID uuid);
    Optional<OperationEntity> findByUuid (UUID uuid);
    void deleteByUuid (UUID uuid);

}
