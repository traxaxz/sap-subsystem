package com.sap.subsystem.vcs_repository.service;

import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.service.SecretService;
import com.sap.subsystem.vcs_repository.domain.mapping.VcsRepositoryMapper;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.common.error.exception.EntityNotFoundException;
import com.sap.subsystem.vcs_repository.repository.VcsRepositoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Service handling and managing data of type {@link VcsRepository}
 * <p>
 *     This service is responsible for communication with {@link VcsRepositoryRepo} interface,
 *     storing and managing data in Database.
 * </p>
 *
 * @author danail.zlatanov
 * */
@Service
public class VcsRepositoryService {
    private final VcsRepositoryRepo vcsRepositoryRepo;
    private final SecretService secretService;

    public VcsRepositoryService(final VcsRepositoryRepo vcsRepositoryRepo, final SecretService secretService) {
        this.vcsRepositoryRepo = vcsRepositoryRepo;
        this.secretService = secretService;
    }

    public void saveVcsRepository(final VcsRepository vcsRepository){
        vcsRepositoryRepo.save(vcsRepository);
    }

    public void update(final VcsRepository vcsRepositoryForUpdate, final Set<UUID> secrets){
        final Set<Secret> foundSecrets = secretService.findByBusinessIdsIn(secrets);
        mapSecrets(vcsRepositoryForUpdate, foundSecrets);

        vcsRepositoryRepo.save(vcsRepositoryForUpdate);
    }

    public VcsRepository getByBusinessId(final UUID businessId){
       return vcsRepositoryRepo.getByBusinessId(businessId)
               .orElseThrow(EntityNotFoundException::new);
    }
    public boolean doesRepositoryExist(final String repository){
        return vcsRepositoryRepo.existsByRepository(repository);
    }

    public List<VcsRepository> findAllVcsRepositories(){
       return vcsRepositoryRepo.findAll();
    }

    public void delete(final UUID businessId) {
        final VcsRepository vcsRepository = vcsRepositoryRepo.getByBusinessId(businessId)
                .orElseThrow(EntityNotFoundException::new);
        vcsRepositoryRepo.delete(vcsRepository);
    }

    private void mapSecrets(final VcsRepository vcsRepository, final Iterable<Secret> secrets) {
        secrets.forEach(vcsRepository::addSecret);
    }
}
