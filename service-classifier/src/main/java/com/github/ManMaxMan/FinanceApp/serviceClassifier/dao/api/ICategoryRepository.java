package com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.api;

import com.github.ManMaxMan.FinanceApp.serviceClassifier.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    Boolean existsByTitle (String name);
    Boolean existsByUuid (UUID uuid);
}
