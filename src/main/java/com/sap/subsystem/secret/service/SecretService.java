package com.sap.subsystem.secret.service;

import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.common.error.exception.EntityNotFoundException;
import com.sap.subsystem.github_api.service.GithubSecretApiService;
import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.domain.mapping.SecretMapper;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.repository.SecretRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * Secret Service responsible for operating and handling secrets data
 *
 * @author danail.zlatanov
 * */
@Service
@Transactional(readOnly = true)
public class SecretService {
    private final SecretRepository secretRepository;
    private final SecretMapper secretMapper;
    public SecretService(SecretRepository secretRepository, SecretMapper secretMapper) {
        this.secretRepository = secretRepository;
        this.secretMapper = secretMapper
    }

    public SecretView getByBusinessId(final UUID businessId){
        final Secret secret = findByBusinessId(businessId);
        return secretMapper.toView(secret);
    }

    public Set<Secret> findByBusinessIdsIn(final Set<UUID> secrets){
        return secretRepository.findByBusinessIdIn(secrets);
    }

    private Secret findByBusinessId(final UUID businessId){
        return secretRepository.findByBusinessId(businessId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<SecretView> listAllSecrets(){
        final List<Secret> secrets = secretRepository.findAll();
        return secretMapper.toViews(secrets);
    }

    @Transactional
    public void createSecret(final SecretDto secretDto){
        validateSecret(secretDto);
        final Secret secret = secretMapper.toEntity(secretDto);
        secretRepository.save(secret);
    }

    @Transactional
    public void deleteSecret(final UUID businessId){
       final Secret secret = findByBusinessId(businessId);
       secretRepository.delete(secret);
    }

    private void validateSecret(final SecretDto secretDto){
        boolean existsBySecret = secretRepository.existsBySecret(secretDto.secret());
        if(existsBySecret){
            throw new DuplicateEntityException();
        }
    }
}
