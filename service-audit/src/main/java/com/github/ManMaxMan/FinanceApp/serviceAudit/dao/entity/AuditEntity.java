package com.github.ManMaxMan.FinanceApp.serviceAudit.dao.entity;

import com.github.ManMaxMan.FinanceApp.serviceAudit.core.enums.ETypeEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "audit", schema = "app")
@Entity
public class AuditEntity {

    @Id
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "user_uuid")
    private UUID userUuid;

    private String text;

    @Column(name = "type_entity")
    @Enumerated(EnumType.STRING)
    private ETypeEntity typeEntity;

    @Column(name = "uuid_entity")
    private UUID uuidEntity;

    public AuditEntity() {
    }

    public AuditEntity(UUID uuid, LocalDateTime dtCreate, UUID userUuid, String text, ETypeEntity typeEntity, UUID uuidEntity) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.userUuid = userUuid;
        this.text = text;
        this.typeEntity = typeEntity;
        this.uuidEntity = uuidEntity;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ETypeEntity getTypeEntity() {
        return typeEntity;
    }

    public void setTypeEntity(ETypeEntity typeEntity) {
        this.typeEntity = typeEntity;
    }

    public UUID getUuidEntity() {
        return uuidEntity;
    }

    public void setUuidEntity(UUID uuidEntity) {
        this.uuidEntity = uuidEntity;
    }
}
