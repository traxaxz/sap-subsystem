package com.sap.subsystem.github_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublicKeyDto(
        @JsonProperty("key_id")
        String keyId,
        String key
) {
}
