package com.sap.subsystem.secret.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record SecretDto(
        @NotBlank(message = "Secret must not be empty")
        String secret
) {
}
