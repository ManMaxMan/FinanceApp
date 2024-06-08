package com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="operation",schema = "app")
public class OperationEntity {

    @Id
    private UUID uuid;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "account_uuid")
    private AccountEntity accountUuid;


    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Column(name = "dt_execute")
    private LocalDateTime dtExecute;

    private String description;

    @Column(name = "category_uuid")
    private UUID categoryUuid;

    private Double value;

    @Column(name = "currency_uuid")
    private UUID currencyUuid;

    public OperationEntity() {
    }

    public OperationEntity(UUID uuid, AccountEntity accountUuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, LocalDateTime dtExecute, String description, UUID categoryUuid, Double value, UUID currencyUuid) {
        this.uuid = uuid;
        this.accountUuid = accountUuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.dtExecute = dtExecute;
        this.description = description;
        this.categoryUuid = categoryUuid;
        this.value = value;
        this.currencyUuid = currencyUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public AccountEntity getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(AccountEntity accountUuid) {
        this.accountUuid = accountUuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public LocalDateTime getDtExecute() {
        return dtExecute;
    }

    public void setDtExecute(LocalDateTime dtExecute) {
        this.dtExecute = dtExecute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategoryUuid() {
        return categoryUuid;
    }

    public void setCategoryUuid(UUID categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public UUID getCurrencyUuid() {
        return currencyUuid;
    }

    public void setCurrencyUuid(UUID currencyUuid) {
        this.currencyUuid = currencyUuid;
    }
}
