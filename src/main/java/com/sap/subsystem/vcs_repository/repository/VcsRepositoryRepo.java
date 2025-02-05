package com.sap.subsystem.vcs_repository.repository;

import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VcsRepositoryRepo extends JpaRepository<VcsRepository, Long> {

    boolean existsByRepository(final String repository);

    Optional<VcsRepository> getByBusinessId(final String businessId);
}
