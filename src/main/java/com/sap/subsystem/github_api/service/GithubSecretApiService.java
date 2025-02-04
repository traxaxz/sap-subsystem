package com.sap.subsystem.github_api.service;

import com.sap.subsystem.config.GithubProperties;
import com.sap.subsystem.github_api.api.GithubSecretApi;
import com.sap.subsystem.github_api.domain.dto.GithubSecretApiResponseDto;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GithubSecretApiService {
    private final GithubSecretApi githubSecretApi;
    private final GithubProperties githubProperties;

    public GithubSecretApiService(final GithubSecretApi githubSecretApi, final GithubProperties githubProperties) {
        this.githubSecretApi = githubSecretApi;
        this.githubProperties = githubProperties;
    }

    public ResponseEntity<Void> updateSecret(final String repositoryName, final String secretName){
        return githubSecretApi.updateSecret(githubProperties.getOwner(), repositoryName, secretName);
    }

    public GithubSecretApiResponseDto getSecretByRepository(final String repositoryName){
        return githubSecretApi.getSecretByRepository(githubProperties.getOwner(), repositoryName);
    }

}
