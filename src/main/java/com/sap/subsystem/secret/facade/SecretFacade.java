package com.sap.subsystem.secret.facade;

import com.sap.subsystem.common.error.exception.GithubException;
import com.sap.subsystem.github_api.service.GithubSecretApiService;
import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.domain.mapping.SecretMapper;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.service.SecretService;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import com.sap.subsystem.vcs_repository.service.VcsRepositoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    public SecretView getByBusinessId(final UUID businessId){
        final Secret secret = secretService.getByBusinessId(businessId);
        return secretMapper.toView(secret);
    }

    public List<SecretView> listAllSecrets(){
        final List<Secret> secrets = secretService.listAllSecrets();
        return secretMapper.toViews(secrets);
    }

    @Transactional
    public void createSecret(final SecretDto secretDto){
        secretService.validateForCreate(secretDto);
        final Secret secret = secretMapper.toEntity(secretDto);
        secretService.save(secret);
    }

    @Transactional
    public void updateSecret(final UUID businessId, final SecretDto secretDto){
        secretService.validateForUpdate(businessId, secretDto);
        final VcsRepository vcsRepository = vcsRepositoryService.getByBusinessId(secretDto.repositoryId());
        final ResponseEntity<Void> updateSecretResponse = githubSecretApiService.updateSecret(vcsRepository.getRepository(), secretDto.secret());

        if(updateSecretResponse.getStatusCode() != HttpStatus.OK){
            throw new GithubException("Failed to create/update secret");
        }

        final Secret secretForUpdate = secretService.getByBusinessId(businessId);
        final Secret secret = secretMapper.toEntity(secretDto);

        secretMapper.update(secretForUpdate, secret);
        secretService.save(secretForUpdate);
    }

    @Transactional
    public void deleteSecret(final UUID businessId){
        secretService.deleteSecret(businessId);
    }
}
