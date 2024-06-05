package com.github.ManMaxMan.FinanceApp.serviceUser.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IVerificationRepository extends JpaRepository<VerificationEntity, UUID> {

}
