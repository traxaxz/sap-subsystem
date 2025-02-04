package com.sap.subsystem.secret.repository;

import com.sap.subsystem.secret.domain.model.Secret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SecretRepository extends JpaRepository<Secret, Long> {

    Optional<Secret> findByBusinessId(final UUID businessId);

    boolean existsBySecret(final String secret);

    List<Secret> findByBusinessIdIn(Set<UUID> secrets);
}
