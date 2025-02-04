package com.sap.subsystem.secret.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SecretDto(
        @NotBlank(message = "Secret must not be empty")
        String secret,
        @NotNull(message = "Repository id cannot be empty")
        UUID repositoryId
) {
}
