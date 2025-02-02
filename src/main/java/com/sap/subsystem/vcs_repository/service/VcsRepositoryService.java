package com.sap.subsystem.vcs_repository.service;

import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.service.SecretService;
import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.domain.mapping.VcsRepositoryMapper;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.common.error.exception.EntityNotFoundException;
import com.sap.subsystem.vcs_repository.repository.VcsRepositoryRepo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Service handling and managing data of type {@link VcsRepository}
 * <p>
 *     This service communicates with {@link VcsRepositoryRepo} interface for storing and managing data in Database.
 *     {@link VcsRepositoryService} uses {@link VcsRepositoryMapper} for mapping DTO'S, Views and Entities
 * </p>
 *
 * @author danail.zlatanov
 * */
@Service
@Transactional(readOnly = true)
public class VcsRepositoryService {
    private final VcsRepositoryRepo vcsRepositoryRepo;
    private final VcsRepositoryMapper vcsRepositoryMapper;
    private final SecretService secretService;

    public VcsRepositoryService(VcsRepositoryRepo vcsRepositoryRepo, VcsRepositoryMapper vcsRepositoryMapper, SecretService secretService) {
        this.vcsRepositoryRepo = vcsRepositoryRepo;
        this.vcsRepositoryMapper = vcsRepositoryMapper;
        this.secretService = secretService;
    }

    @Transactional
    public void saveVcsRepository(final VcsRepositoryDto vcsRepositoryDto){
        //TODO check for repository if exists in VCS
        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(vcsRepositoryDto);
        validateRepository(vcsRepository.getRepository());
        vcsRepositoryRepo.save(vcsRepository);
    }

    @Transactional
    public void editVcsRepository(final UUID businessId, final EditVcsRepositoryDto vcsRepositoryDto){
        final VcsRepository vcsRepositoryForUpdate = vcsRepositoryRepo.getByBusinessId(businessId)
                .orElseThrow(EntityNotFoundException::new);

        if(ObjectUtils.isNotEmpty(vcsRepositoryDto.repository()) && !vcsRepositoryDto.repository().equals(vcsRepositoryForUpdate.getRepository())){
            validateRepository(vcsRepositoryDto.repository());
        }


        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(vcsRepositoryDto);
        vcsRepositoryMapper.update(vcsRepositoryForUpdate, vcsRepository);
        final Set<Secret> secrets = secretService.findByBusinessIdsIn(vcsRepositoryDto.secrets());
        mapSecrets(vcsRepositoryForUpdate, secrets);

        vcsRepositoryRepo.save(vcsRepositoryForUpdate);
    }

    public VcsRepositoryView getByBusinessId(final UUID businessId){
       return vcsRepositoryRepo.getByBusinessId(businessId)
                .map(vcsRepositoryMapper::toView)
               .orElseThrow(EntityNotFoundException::new);
    }

    public List<VcsRepositoryView> listAllVcsRepositories(){
        final List<VcsRepository> vcsRepositories = vcsRepositoryRepo.findAll();
        return vcsRepositoryMapper.toViews(vcsRepositories);
    }
    @Transactional
    public void deleteVcsRepository(final UUID businessId) {
        final VcsRepository vcsRepository = vcsRepositoryRepo.getByBusinessId(businessId)
                .orElseThrow(EntityNotFoundException::new);
        vcsRepositoryRepo.delete(vcsRepository);
    }

    private void validateRepository(final String repository){
        boolean existsByRepository = vcsRepositoryRepo.existsByRepository(repository);
        if(existsByRepository){
            throw new DuplicateEntityException();
        }
    }

    private void mapSecrets(final VcsRepository vcsRepository, final Set<Secret> secrets) {
        secrets.forEach(vcsRepository::addSecret);
    }
}
