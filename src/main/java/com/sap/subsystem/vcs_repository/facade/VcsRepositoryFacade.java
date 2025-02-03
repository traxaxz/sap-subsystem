package com.sap.subsystem.vcs_repository.facade;

import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.github_api.service.GithubRepositoryApiService;
import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.domain.mapping.VcsRepositoryMapper;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import com.sap.subsystem.vcs_repository.service.VcsRepositoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
public class VcsRepositoryFacade {
    private final VcsRepositoryService vcsRepositoryService;
    private final VcsRepositoryMapper vcsRepositoryMapper;
    private final GithubRepositoryApiService githubRepositoryApiService;

    public VcsRepositoryFacade(VcsRepositoryService vcsRepositoryService, VcsRepositoryMapper vcsRepositoryMapper,
                               GithubRepositoryApiService githubRepositoryApiService) {
        this.vcsRepositoryService = vcsRepositoryService;
        this.vcsRepositoryMapper = vcsRepositoryMapper;
        this.githubRepositoryApiService = githubRepositoryApiService;
    }

    @Transactional
    public void createVcsRepository(final VcsRepositoryDto vcsRepositoryDto){
        githubRepositoryApiService.createRepository(vcsRepositoryDto);
        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(vcsRepositoryDto);
        validateRepository(vcsRepository.getRepository());
        vcsRepositoryService.saveVcsRepository(vcsRepository);
    }

    @Transactional
    public void editVcsRepository(final UUID businessId, final EditVcsRepositoryDto vcsRepositoryDto){
        final VcsRepository vcsRepositoryForUpdate = vcsRepositoryService.getByBusinessId(businessId);
        githubRepositoryApiService.updateRepository(vcsRepositoryForUpdate.getRepository(), vcsRepositoryDto);

        if(ObjectUtils.isNotEmpty(vcsRepositoryDto.repository())
                && !vcsRepositoryDto.repository().equals(vcsRepositoryForUpdate.getRepository())){
            validateRepository(vcsRepositoryDto.repository());
        }

        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(vcsRepositoryDto);
        vcsRepositoryMapper.update(vcsRepositoryForUpdate, vcsRepository);
        vcsRepositoryService.update(vcsRepositoryForUpdate, vcsRepositoryDto.secrets());
    }

    public List<VcsRepositoryView> listAllVcsRepositories(){
        final List<VcsRepository> vcsRepositories = vcsRepositoryService.findAllVcsRepositories();
        return vcsRepositoryMapper.toViews(vcsRepositories);
    }

    public VcsRepositoryView getByBusinessId(final UUID businessId) {
        final VcsRepository vcsRepository = vcsRepositoryService.getByBusinessId(businessId);
        return vcsRepositoryMapper.toView(vcsRepository);
    }

    @Transactional
    public void deleteVcsRepository(final UUID businessId) {
        final VcsRepository repository = vcsRepositoryService.getByBusinessId(businessId);
        githubRepositoryApiService.deleteRepository(repository.getRepository());
        vcsRepositoryService.delete(businessId);
    }

    private void validateRepository(final String repository){
        boolean existsByRepository = vcsRepositoryService.doesRepositoryExist(repository);
        if(existsByRepository){
            throw new DuplicateEntityException();
        }
    }
}
