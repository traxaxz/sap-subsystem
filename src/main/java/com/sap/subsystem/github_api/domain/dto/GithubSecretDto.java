package com.sap.subsystem.github_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubSecretDto(
        String name,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("updated_at")
        String updatedAt
) {
}
