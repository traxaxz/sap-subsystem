package com.sap.subsystem.github_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EncryptedSecret(
        @JsonProperty("encrypted_value")
        String encryptedValue,
        @JsonProperty("key_id")
        String keyId
) {
}
