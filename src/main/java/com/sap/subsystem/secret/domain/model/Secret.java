package com.sap.subsystem.secret.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

@Entity
@Table(name = "secret")
public class Secret {

    @Id
    private Long id;

    @Generated(event = EventType.INSERT)
    private String businessId;

    private String secret;

    public Long getId() {
        return id;
    }

    public Secret setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBusinessId() {
        return businessId;
    }

    public Secret setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public Secret setSecret(String secret) {
        this.secret = secret;
        return this;
    }
}
