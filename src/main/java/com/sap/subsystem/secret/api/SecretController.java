package com.sap.subsystem.secret.api;

import java.util.List;

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

    @PostMapping
    public void createSecret(@RequestBody @Valid final SecretDto secretDto){
        secretFacade.createSecret(secretDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSecret(@PathVariable final String id) {
        secretFacade.deleteSecret(id);
    }
}
