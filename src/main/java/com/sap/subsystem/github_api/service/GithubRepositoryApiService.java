package com.sap.subsystem.github_api.service;

import com.sap.subsystem.config.GithubProperties;
import com.sap.subsystem.github_api.api.GitHubRepositoryApi;
import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiRequestDto;
import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiResponseDto;
import com.sap.subsystem.github_api.domain.mapping.GithubApiMapper;
import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GithubRepositoryApiService {
    private final GitHubRepositoryApi gitHubRepositoryApi;
    private final GithubProperties githubProperties;
    private final GithubApiMapper githubApiMapper;

    public GithubRepositoryApiService(final GitHubRepositoryApi gitHubRepositoryApi, final GithubProperties githubProperties, final GithubApiMapper githubApiMapper) {
        this.gitHubRepositoryApi = gitHubRepositoryApi;
        this.githubProperties = githubProperties;
        this.githubApiMapper = githubApiMapper;
    }

    public GithubRepositoryApiResponseDto createRepository(final VcsRepositoryDto vcsRepositoryDto){
        final GithubRepositoryApiRequestDto githubRepositoryApiRequestDto = githubApiMapper.toGithubDto(vcsRepositoryDto);
        return gitHubRepositoryApi.createRepository(githubRepositoryApiRequestDto);
    }

    public GithubRepositoryApiResponseDto updateRepository(final String repoName, final EditVcsRepositoryDto vcsRepositoryDto){
        final GithubRepositoryApiRequestDto githubRepositoryApiRequestDto = githubApiMapper.toGithubDto(vcsRepositoryDto);
        return gitHubRepositoryApi.updateRepository(githubProperties.getOwner(), repoName, githubRepositoryApiRequestDto);
    }

    public GithubRepositoryApiResponseDto getAllRepositories(){
        return gitHubRepositoryApi.getAllRepositories(githubProperties.getOwner());
    }
    public ResponseEntity<Void> deleteRepository(final String repoName){
       return gitHubRepositoryApi.deleteRepository(githubProperties.getOwner(), repoName);
    }

}
