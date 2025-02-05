package com.sap.subsystem.github_api.service;

import com.sap.subsystem.config.GithubProperties;
import com.sap.subsystem.github_api.api.GithubSecretApi;
import com.sap.subsystem.github_api.domain.dto.EncryptedSecret;
import com.sap.subsystem.github_api.domain.dto.GithubSecretApiResponseDto;

import com.sap.subsystem.github_api.domain.dto.PublicKeyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.sap.subsystem.github_api.util.SecretEncryptionUtil.encryptSecret;

@Service
public class GithubSecretApiService {
    private final GithubSecretApi githubSecretApi;
    private final GithubProperties githubProperties;

    public GithubSecretApiService(final GithubSecretApi githubSecretApi, final GithubProperties githubProperties) {
        this.githubSecretApi = githubSecretApi;
        this.githubProperties = githubProperties;
    }

    public ResponseEntity<Void> updateSecret(final String repositoryName, final String secretName){

        final PublicKeyDto publicKey = githubSecretApi.getPublicKey(githubProperties.getOwner(), repositoryName);
        final String encryptedValue = encryptSecret(publicKey.key(), secretName);
        final EncryptedSecret encryptedSecret = new EncryptedSecret(encryptedValue, publicKey.keyId());

        return githubSecretApi.updateSecret(githubProperties.getOwner(), repositoryName, secretName, encryptedSecret);
    }

    public GithubSecretApiResponseDto getSecretByRepository(final String repositoryName){
        return githubSecretApi.getSecretByRepository(githubProperties.getOwner(), repositoryName);
    }

    public GithubSecretApiResponseDto getSecrets(final String repoName) {
        return githubSecretApi.getSecrets(githubProperties.getOwner(), repoName);
    }

    public ResponseEntity<Void> deleteSecret(final String repoName, final String secret){
        return githubSecretApi.deleteSecret(githubProperties.getOwner(), repoName, secret);
    }
}
