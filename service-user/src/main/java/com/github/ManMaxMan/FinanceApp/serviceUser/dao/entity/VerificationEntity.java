package com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "verification", schema = "app")
@Entity
public class VerificationEntity {

    @Id
    private UUID uuid;

    @Column(name = "verification_code")
    private String verificationCode;

    public VerificationEntity() {
    }

    public VerificationEntity(UUID uuid, String verificationCode) {
        this.uuid = uuid;
        this.verificationCode = verificationCode;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
