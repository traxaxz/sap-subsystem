package com.sap.subsystem.secret.domain.model;

import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.util.UUID;

@Entity
@Table(name = "secret")
public class Secret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Generated(event = EventType.INSERT)
    private UUID businessId;

    private String secret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repository_id", referencedColumnName = "id")
    private VcsRepository vcsRepository;

    public Long getId() {
        return id;
    }

    public Secret setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getBusinessId() {
        return businessId;
    }

    public Secret setBusinessId(UUID businessId) {
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

    public VcsRepository getVcsRepository() {
        return vcsRepository;
    }

    public Secret setVcsRepository(VcsRepository vcsRepository) {
        this.vcsRepository = vcsRepository;
        return this;
    }
}
