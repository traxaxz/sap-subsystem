package com.sap.subsystem.secret.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record SecretDto(
        @NotBlank(message = "Secret must not be empty")
        String secret,
        @NotNull(message = "Repository id cannot be empty")
        String repositoryId
) {
}
