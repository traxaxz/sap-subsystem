package com.sap.subsystem.vcs_repository.service;

import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.domain.mapping.VcsRepositoryMapper;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import com.sap.subsystem.vcs_repository.error.exception.DuplicateEntityException;
import com.sap.subsystem.vcs_repository.error.exception.EntityNotFoundException;
import com.sap.subsystem.vcs_repository.repository.VcsRepositoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public VcsRepositoryService(VcsRepositoryRepo vcsRepositoryRepo, VcsRepositoryMapper vcsRepositoryMapper) {
        this.vcsRepositoryRepo = vcsRepositoryRepo;
        this.vcsRepositoryMapper = vcsRepositoryMapper;
    }

    @Transactional
    public void saveVcsRepository(final VcsRepositoryDto vcsRepositoryDto){
        //TODO check for repository if exists in VCS
        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(vcsRepositoryDto);
        validateRepository(vcsRepository);
        vcsRepositoryRepo.save(vcsRepository);
    }

    @Transactional
    public void editVcsRepository(final String businessId, final VcsRepositoryDto vcsRepositoryDto){
        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(vcsRepositoryDto);
        validateRepository(vcsRepository);
        final VcsRepository vcsRepositoryForUpdate = vcsRepositoryRepo.getByBusinessId(businessId)
                .orElseThrow(EntityNotFoundException::new);
        final VcsRepository updatedVcsRepository = vcsRepositoryMapper.update(vcsRepositoryForUpdate, vcsRepository);

        vcsRepositoryRepo.save(updatedVcsRepository);
    }

    public VcsRepositoryView getByBusinessId(final String businessId){
       return vcsRepositoryRepo.getByBusinessId(businessId)
                .map(vcsRepositoryMapper::toView)
               .orElseThrow(EntityNotFoundException::new);
    }

    public List<VcsRepositoryView> listAllVcsRepositories(){
        final List<VcsRepository> vcsRepositories = vcsRepositoryRepo.findAll();
        return vcsRepositoryMapper.toViews(vcsRepositories);
    }

    public void deleteVcsRepository(final String businessId) {
        final VcsRepositoryView vcsRepositoryView = getByBusinessId(businessId);
        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(vcsRepositoryView);
        vcsRepositoryRepo.delete(vcsRepository);
    }

    private void validateRepository(final VcsRepository vcsRepository){
        existsByBusinessId(vcsRepository.getBusinessId());
    }

    private void existsByBusinessId(final String businessId) {
        final boolean existsByRepository = vcsRepositoryRepo.existsByRepository(businessId);
        if(existsByRepository){
            throw new DuplicateEntityException();
        }
    }
}
