package com.sap.subsystem.secret.api;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.service.SecretService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secret")
public class SecretController {
    private final SecretService secretService;

    public SecretController(final SecretService secretService) {
        this.secretService = secretService;
    }

    @GetMapping("/{id}")
    public SecretView getSecretById(@PathVariable final UUID id) {
        return secretService.getByBusinessId(id);
    }

    @GetMapping
    public List<SecretView> listAllSecrets() {
        return secretService.listAllSecrets();
    }

    @PostMapping
    public void createSecret(@RequestBody @Valid final SecretDto secretDto) {
        secretService.createSecret(secretDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSecret(@PathVariable final UUID id) {
        secretService.deleteSecret(id);
    }
}
