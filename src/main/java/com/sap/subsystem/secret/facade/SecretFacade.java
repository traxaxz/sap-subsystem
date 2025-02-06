package com.sap.subsystem.secret.facade;

import com.sap.subsystem.common.error.exception.GithubException;
import com.sap.subsystem.github_api.service.GithubSecretApiService;
import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.mapping.SecretMapper;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.service.SecretService;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import com.sap.subsystem.vcs_repository.service.VcsRepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SecretFacade {

    private final SecretMapper secretMapper;
    private final SecretService secretService;
    private final GithubSecretApiService githubSecretApiService;
    private final VcsRepositoryService vcsRepositoryService;


    public SecretFacade(final SecretMapper secretMapper, final SecretService secretService, final GithubSecretApiService githubSecretApiService, final VcsRepositoryService vcsRepositoryService) {
        this.secretMapper = secretMapper;
        this.secretService = secretService;
        this.githubSecretApiService = githubSecretApiService;
        this.vcsRepositoryService = vcsRepositoryService;
    }

    @Transactional
    public void createSecret(final SecretDto secretDto){
        secretService.validateForCreate(secretDto);
        final VcsRepository vcsRepository = vcsRepositoryService.getByBusinessId(secretDto.repositoryId());
        final ResponseEntity<Void> response = githubSecretApiService.updateSecret(vcsRepository.getRepository(), secretDto.secret());

        if(response.getStatusCode() != HttpStatus.CREATED){
            throw new GithubException("Failed to create secret");
        }
        final Secret secret = secretMapper.toEntity(secretDto);
        secret.setVcsRepository(vcsRepository);
        secretService.save(secret);
    }


    @Transactional
    public void deleteSecret(final String businessId){
        final Secret secret = secretService.getByBusinessId(businessId);
        githubSecretApiService.deleteSecret(secret.getVcsRepository().getRepository(), secret.getSecret());
        secretService.deleteSecret(secret);
    }
}

