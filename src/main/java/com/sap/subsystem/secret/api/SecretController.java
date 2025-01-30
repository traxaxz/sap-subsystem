package com.sap.subsystem.secret.api;

import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.service.SecretService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secret")
public class SecretController {
    private final SecretService secretService;

    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    @GetMapping("/{id}")
    public SecretView getSecretById(@PathVariable final String id){
        return secretService.getByBusinessId(id);
    }

    @GetMapping
    public List<SecretView> listAllSecrets(){
        return secretService.listAllSecrets();
    }

    @PostMapping
    public void createSecret(@RequestBody @Valid final SecretDto secretDto){
        secretService.createSecret(secretDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSecret(@PathVariable final String id){
        secretService.getByBusinessId(id);
    }
}
