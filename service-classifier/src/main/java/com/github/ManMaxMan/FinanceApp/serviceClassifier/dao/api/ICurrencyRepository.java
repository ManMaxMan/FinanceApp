package com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ICurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {
    Boolean existsByTitle (String name);
}
