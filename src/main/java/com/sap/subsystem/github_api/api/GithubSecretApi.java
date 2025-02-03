package com.sap.subsystem.github_api.api;

import com.sap.subsystem.config.GithubFeignInterceptor;
import com.sap.subsystem.github_api.domain.dto.GithubSecretApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name ="githubClient", url = "${github.api_url}", configuration = GithubFeignInterceptor.class)
public interface GithubSecretApi {

    @GetMapping("/repos/{owner}/{repo}/actions/organization-secrets")
    GithubSecretApiResponseDto getSecretByRepository(@PathVariable final String owner, @PathVariable final String repo);

    @PutMapping("/repos/{owner}/{repo}/actions/secrets/{secretName}")
    void updateSecret(@PathVariable final String owner, @PathVariable final String repo, @PathVariable final String secretName);

    @DeleteMapping("/repos/{owner}/{repo}/actions/secrets/{secretName}")
    void deleteSecret(@PathVariable final String owner, @PathVariable final String repo, @PathVariable final String secretName);
}
