package com.sap.subsystem.vcs_repository.facade;

import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.common.error.exception.GithubException;
import com.sap.subsystem.common.error.exception.InvalidSecretException;
import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiResponseDto;
import com.sap.subsystem.github_api.service.GithubRepositoryApiService;
import com.sap.subsystem.secret.facade.SecretFacade;
import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.domain.mapping.VcsRepositoryMapper;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import com.sap.subsystem.vcs_repository.service.VcsRepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class VcsRepositoryFacade {

    private final VcsRepositoryService vcsRepositoryService;
    private final VcsRepositoryMapper vcsRepositoryMapper;
    private final GithubRepositoryApiService githubRepositoryApiService;
    private final SecretFacade secretFacade;

    public VcsRepositoryFacade(final VcsRepositoryService vcsRepositoryService, final VcsRepositoryMapper vcsRepositoryMapper,
                               final GithubRepositoryApiService githubRepositoryApiService, SecretFacade secretFacade) {
        this.vcsRepositoryService = vcsRepositoryService;
        this.vcsRepositoryMapper = vcsRepositoryMapper;
        this.githubRepositoryApiService = githubRepositoryApiService;
        this.secretFacade = secretFacade;
    }

    @Transactional
    public void createVcsRepository(final VcsRepositoryDto vcsRepositoryDto){
        validateRepository(vcsRepositoryDto.repository());
        final GithubRepositoryApiResponseDto createdRepository = githubRepositoryApiService.createRepository(vcsRepositoryDto);
        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(createdRepository);
        vcsRepositoryService.saveVcsRepository(vcsRepository);
    }

    @Transactional
    public void editVcsRepository(final String businessId, final EditVcsRepositoryDto vcsRepositoryDto){
        final VcsRepository vcsRepositoryForUpdate = vcsRepositoryService.getByBusinessId(businessId);

        if(!vcsRepositoryDto.repository().equals(vcsRepositoryForUpdate.getRepository())){
            validateRepository(vcsRepositoryDto.repository());
        }
        final GithubRepositoryApiResponseDto updatedRepository = githubRepositoryApiService.updateRepository(vcsRepositoryForUpdate.getRepository(), vcsRepositoryDto);
        final VcsRepository vcsRepository = vcsRepositoryMapper.toEntity(updatedRepository);

        vcsRepositoryMapper.update(vcsRepositoryForUpdate, vcsRepository);
        vcsRepositoryService.update(vcsRepositoryForUpdate, vcsRepositoryDto.secrets());
    }

    public List<VcsRepositoryView> listAllVcsRepositories(){
        final List<VcsRepository> vcsRepositories = vcsRepositoryService.findAllVcsRepositories();
        return vcsRepositoryMapper.toViews(vcsRepositories);
    }

    public VcsRepositoryView getByBusinessId(final String businessId) {
        final VcsRepository vcsRepository = vcsRepositoryService.getByBusinessId(businessId);
        return vcsRepositoryMapper.toView(vcsRepository);
    }

    @Transactional
    public void deleteVcsRepository(final String businessId) {
        final VcsRepository foundRepository = vcsRepositoryService.getByBusinessId(businessId);
        final ResponseEntity<Void> response = githubRepositoryApiService.deleteRepository(foundRepository.getRepository());
        if(response.getStatusCode() == HttpStatus.NO_CONTENT){
            vcsRepositoryService.delete(businessId);
        } else {
            throw new GithubException("Failed to delete repository");
        }
    }

    public void validateSecret(final VcsRepositoryDto vcsRepositoryDto) {
        final List<VcsRepositoryView> repositories = listAllVcsRepositories();
        final boolean isValid = repositories.stream()
                .filter(repoView -> repoView.repository().equals(vcsRepositoryDto.repository()))
                .anyMatch(repoView -> repoView.secrets().stream()
                        .anyMatch(secretView ->
                                secretView.secret().equals(vcsRepositoryDto.secret().toString())
                        )
                );

        if(!isValid){
            throw new InvalidSecretException();
        }
    }

    private void validateRepository(final String repository){
        final boolean existsByRepository = vcsRepositoryService.doesRepositoryExist(repository);
        if(existsByRepository){
            throw new DuplicateEntityException();
        }
    }
}
