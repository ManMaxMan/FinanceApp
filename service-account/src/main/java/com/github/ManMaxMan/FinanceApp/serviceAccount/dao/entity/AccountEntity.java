package com.github.ManMaxMan.FinanceApp.serviceAccount.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ManMaxMan.FinanceApp.serviceAccount.core.enums.EAccountType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="account",schema = "app")

public class AccountEntity {

    @Id
    private UUID uuid;

    @Column(name = "user_uuid")
    private UUID userUuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    private String title;

    private String description;

    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_account")
    private EAccountType typeAccount;

    @Column(name = "currency_uuid")
    private UUID currencyUuid;

    @OneToMany(mappedBy = "accountUuid")
    @JsonIgnore
    private List<OperationEntity> operationEntities;

    public AccountEntity() {
    }

    public AccountEntity(UUID uuid, UUID userUuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String title, String description, double balance, EAccountType typeAccount, UUID currencyUuid, List<OperationEntity> operationEntities) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.description = description;
        this.balance = balance;
        this.typeAccount = typeAccount;
        this.currencyUuid = currencyUuid;
        this.operationEntities = operationEntities;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public EAccountType getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(EAccountType typeAccount) {
        this.typeAccount = typeAccount;
    }

    public UUID getCurrencyUuid() {
        return currencyUuid;
    }

    public void setCurrencyUuid(UUID currencyUuid) {
        this.currencyUuid = currencyUuid;
    }

    public List<OperationEntity> getOperationEntities() {
        return operationEntities;
    }

    public void setOperationEntities(List<OperationEntity> operationEntities) {
        this.operationEntities = operationEntities;
    }
}
