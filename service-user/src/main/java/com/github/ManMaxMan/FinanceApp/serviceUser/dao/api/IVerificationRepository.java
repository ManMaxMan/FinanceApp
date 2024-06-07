package com.github.ManMaxMan.FinanceApp.serviceUser.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface IVerificationRepository extends JpaRepository<VerificationEntity, UUID> {

}
