package com.sap.subsystem.github_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record GithubRepositoryApiResponseDto(
        String name,
        @JsonProperty("node_id")
        String businessId
){}
