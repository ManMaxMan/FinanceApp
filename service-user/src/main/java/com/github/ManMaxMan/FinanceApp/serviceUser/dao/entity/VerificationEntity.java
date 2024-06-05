package com.github.ManMaxMan.FinanceApp.serviceUser.dao.entity;


import com.github.ManMaxMan.FinanceApp.serviceUser.core.enums.EMessageStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "verification", schema = "app")
@Entity
public class VerificationEntity {

    @Id
    private UUID uuid;

    @OneToOne (fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "uuid")
    private UserEntity userEntity;

    @Column(name = "verification_code")
    private String verificationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status")
    private EMessageStatus messageStatus;

    public VerificationEntity() {
    }

    public VerificationEntity(UUID uuid, UserEntity userEntity, String verificationCode, EMessageStatus messageStatus) {
        this.uuid = uuid;
        this.userEntity = userEntity;
        this.verificationCode = verificationCode;
        this.messageStatus = messageStatus;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public EMessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(EMessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }
}
