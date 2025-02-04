package com.sap.subsystem.secret.api;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.service.SecretService;

import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public void updateSecret(@RequestBody @Valid final SecretDto secretDto) {
        secretService.updateSecret(secretDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSecret(@PathVariable final UUID id) {
        secretService.deleteSecret(id);
    }
}
