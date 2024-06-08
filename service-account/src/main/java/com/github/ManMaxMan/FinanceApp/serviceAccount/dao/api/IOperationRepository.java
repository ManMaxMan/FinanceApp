package com.github.ManMaxMan.FinanceApp.serviceAccount.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOperationRepository extends JpaRepository<OperationEntity, UUID> {
}
