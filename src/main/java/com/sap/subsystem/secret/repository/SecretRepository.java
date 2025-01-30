package com.sap.subsystem.secret.repository;

import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.domain.model.Secret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretRepository extends JpaRepository<Secret, Long> {

    Optional<Secret> findByBusinessId(final String businessId);
    boolean existsByBusinessId(final String secret);
}
