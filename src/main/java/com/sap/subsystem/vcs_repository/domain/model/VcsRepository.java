package com.sap.subsystem.vcs_repository.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

@Entity
@Table(name = "vcs_repository")
public class VcsRepository {

    @Id
    private Long id;

    @Generated(event = EventType.INSERT)
    private String businessId;

    private String repository;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "secret_id", referencedColumnName = "businessId")
    private Secret secretId;

    public Long getId() {
        return id;
    }

    public VcsRepository setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBusinessId() {
        return businessId;
    }

    public VcsRepository setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public String getRepository() {
        return repository;
    }

    public VcsRepository setRepository(String repository) {
        this.repository = repository;
        return this;
    }

    public Secret getSecretId() {
        return secretId;
    }

    public VcsRepository setSecretId(Secret secretId) {
        this.secretId = secretId;
        return this;
    }
}
