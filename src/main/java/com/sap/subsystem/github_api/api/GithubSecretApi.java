package com.sap.subsystem.github_api.api;

import com.sap.subsystem.config.GithubFeignInterceptor;
import com.sap.subsystem.github_api.domain.dto.EncryptedSecret;
import com.sap.subsystem.github_api.domain.dto.GithubSecretApiResponseDto;
import com.sap.subsystem.github_api.domain.dto.PublicKeyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name ="githubSecretApi", url = "${github.api_url}", configuration = GithubFeignInterceptor.class)
public interface GithubSecretApi {

    @GetMapping("/repos/{owner}/{repo}/actions/secrets/public-key")
    PublicKeyDto getPublicKey(@PathVariable final String owner, @PathVariable final String repo);

    @GetMapping("/repos/{owner}/{repo}/actions/organization-secrets")
    GithubSecretApiResponseDto getSecretByRepository(@PathVariable final String owner, @PathVariable final String repo);

    @PutMapping("/repos/{owner}/{repo}/actions/secrets/{secretName}")
    ResponseEntity<Void> updateSecret(
            @PathVariable final String owner,
            @PathVariable final String repo,
            @PathVariable final String secretName,
            @RequestBody final EncryptedSecret encryptedSecret
            );

    @DeleteMapping("/repos/{owner}/{repo}/actions/secrets/{secretName}")
    void deleteSecret(@PathVariable final String owner, @PathVariable final String repo, @PathVariable final String secretName);

    @GetMapping("/repos/{owner}/{repo}/actions/secrets")
    GithubSecretApiResponseDto getSecrets(@PathVariable final String owner, @PathVariable final String repo);
}
