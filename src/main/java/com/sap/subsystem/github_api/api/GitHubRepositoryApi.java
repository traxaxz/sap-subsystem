package com.sap.subsystem.github_api.api;

import com.sap.subsystem.config.FeignClientConfig;
import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiRequestDto;
import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name ="githubRepoApi", url = "${github.api_url}", configuration = FeignClientConfig.class)
public interface GitHubRepositoryApi {

    @PostMapping("/user/repos")
    GithubRepositoryApiResponseDto createRepository(@RequestBody final GithubRepositoryApiRequestDto request);

    @PatchMapping("/repos/{owner}/{repo}")
    GithubRepositoryApiResponseDto updateRepository(@PathVariable final String owner, @PathVariable final String repo, @RequestBody final GithubRepositoryApiRequestDto request);


    @GetMapping("/users/{owner}/{repo}")
    String getRepository(@PathVariable final String owner, @PathVariable final String repo);

    @DeleteMapping("repos/{owner}/{repo}")
    ResponseEntity<Void> deleteRepository(@PathVariable final String owner, @PathVariable final String repo);
}
