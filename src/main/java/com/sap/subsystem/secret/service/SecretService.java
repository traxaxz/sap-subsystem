package com.sap.subsystem.secret.service;

import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.common.error.exception.EntityNotFoundException;
import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.repository.SecretRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


/**
 * Secret Service responsible for operating and handling secrets data
 *
 * @author danail.zlatanov
 * */
@Service
public class SecretService {
    private final SecretRepository secretRepository;

    public SecretService(final SecretRepository secretRepository) {
        this.secretRepository = secretRepository;
    }

    public Secret getByBusinessId(final UUID businessId){
        return findByBusinessId(businessId);
    }

    public List<Secret> findByBusinessIdsIn(final Set<UUID> secrets){
        return secretRepository.findByBusinessIdIn(secrets);
    }

    public List<Secret> listAllSecrets(){
        return secretRepository.findAll();
    }

    public void save(final Secret secret){
        secretRepository.save(secret);
    }

    public void deleteSecret(final UUID businessId){
       final Secret secret = findByBusinessId(businessId);
       secretRepository.delete(secret);
    }

    public void validateForCreate(final SecretDto secretDto) {
        if(secretRepository.existsBySecret(secretDto.secret())){
            throw new DuplicateEntityException();
        }
    }

    public void validateForUpdate(final UUID businessId, final SecretDto secretDto) {
        final Secret foundSecret = findByBusinessId(businessId);
        if(!foundSecret.getSecret().equals(secretDto.secret())){
            validateSecret(secretDto.secret());
        }
    }

    private void validateSecret(final String secret){
        final boolean exists = secretRepository.existsBySecret(secret);
        if(exists){
            throw new DuplicateEntityException();
        }
    }

    private boolean existsBySecretName(final String secretName){
        return secretRepository.existsBySecret(secretName);
    }

    private Secret findByBusinessId(final UUID businessId){
        return secretRepository.findByBusinessId(businessId)
                .orElseThrow(EntityNotFoundException::new);
    }


}
