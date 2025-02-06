package com.sap.subsystem.github_api.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record GithubRepositoryApiRequestDto(
        String name
) {
}
