package com.sap.subsystem.secret.api;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.facade.SecretFacade;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secret")
public class SecretController {
    private final SecretFacade secretFacade;

    public SecretController(final SecretFacade secretFacade) {
        this.secretFacade = secretFacade;
    }

    @GetMapping("/{id}")
    public SecretView getSecretById(@PathVariable final UUID id) {
        return secretFacade.getByBusinessId(id);
    }

    @GetMapping
    public List<SecretView> listAllSecrets() {
        return secretFacade.listAllSecrets();
    }

    @PostMapping
    public void createSecret(@RequestBody @Valid final SecretDto secretDto){
        secretFacade.createSecret(secretDto);
    }

    @PutMapping("/{id}")
    public void updateSecret(@PathVariable final UUID id, @RequestBody @Valid final SecretDto secretDto) {
        secretFacade.updateSecret(id, secretDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSecret(@PathVariable final UUID id) {
        secretFacade.deleteSecret(id);
    }
}
