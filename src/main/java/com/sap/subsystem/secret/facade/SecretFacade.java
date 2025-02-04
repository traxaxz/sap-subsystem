package com.sap.subsystem.secret.facade;

import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.common.error.exception.EntityNotFoundException;
import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.domain.mapping.SecretMapper;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.repository.SecretRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class SecretFacade {
    private final SecretRepository secretRepository;
    private final SecretMapper secretMapper;

    public SecretService(final SecretRepository secretRepository, final SecretMapper secretMapper) {
        this.secretRepository = secretRepository;
        this.secretMapper = secretMapper;
    }

    public SecretView getByBusinessId(final UUID businessId){
        final Secret secret = findByBusinessId(businessId);
        return secretMapper.toView(secret);
    }

    public Set<Secret> findByBusinessIdsIn(final Set<UUID> secrets){
        return secretRepository.findByBusinessIdIn(secrets);
    }

    public List<SecretView> listAllSecrets(){
        final List<Secret> secrets = secretRepository.findAll();
        return secretMapper.toViews(secrets);
    }

    @Transactional
    public void updateSecret(final SecretDto secretDto){
        validateSecret(secretDto);
        se
        final Secret secret = secretMapper.toEntity(secretDto);
        secretRepository.save(secret);
    }

    @Transactional
    public void deleteSecret(final UUID businessId){
        final Secret secret = findByBusinessId(businessId);
        secretRepository.delete(secret);
    }

    private void validateSecret(final SecretDto secretDto){
        final boolean existsBySecret = secretRepository.existsBySecret(secretDto.secret());
        if(existsBySecret){
            throw new DuplicateEntityException();
        }
    }

    private Secret findByBusinessId(final UUID businessId){
        return secretRepository.findByBusinessId(businessId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
