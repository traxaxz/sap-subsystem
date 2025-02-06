package com.sap.subsystem.secret.service;

import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.common.error.exception.EntityNotFoundException;
import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.repository.SecretRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


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

    public Secret getByBusinessId(final String businessId){
        return findByBusinessId(businessId);
    }

    public List<Secret> findByBusinessIdsIn(final Set<String> secretIds){
        return secretRepository.findByBusinessIdIn(secretIds);
    }

    public void save(final Secret secret){
        secretRepository.save(secret);
    }

    public void deleteSecret(final Secret secret){
       secretRepository.delete(secret);
    }

    public void validateForCreate(final SecretDto secretDto) {
        if(secretRepository.existsBySecret(secretDto.secret())){
            throw new DuplicateEntityException();
        }
    }


    private void validateSecret(final String secret){
        final boolean exists = secretRepository.existsBySecret(secret);
        if(exists){
            throw new DuplicateEntityException();
        }
    }


    private Secret findByBusinessId(final String businessId){
        return secretRepository.findByBusinessId(businessId)
                .orElseThrow(EntityNotFoundException::new);
    }


}
