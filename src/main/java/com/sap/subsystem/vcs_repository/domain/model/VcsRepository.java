package com.sap.subsystem.vcs_repository.domain.model;

import com.sap.subsystem.secret.domain.model.Secret;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "vcs_repository")
public class VcsRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID businessId;

    private String repository;

    @OneToMany(
            mappedBy = "vcsRepository",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Secret> secrets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public VcsRepository setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getBusinessId() {
        return businessId;
    }

    public VcsRepository setBusinessId(UUID businessId) {
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

    public void addSecret(Secret secret) {
        secrets.add(secret);
        secret.setVcsRepository(this);
    }

    public void removeSecret(Secret secret) {
        secrets.remove(secret);
        secret.setVcsRepository(null);
    }

    public Set<Secret> getSecrets() {
        return secrets;
    }

    public VcsRepository setSecrets(Set<Secret> secrets) {
        this.secrets = secrets;
        return this;
    }
}
