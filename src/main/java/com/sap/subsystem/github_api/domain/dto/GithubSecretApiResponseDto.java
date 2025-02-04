package com.sap.subsystem.github_api.domain.dto;

import java.util.List;

public record GithubSecretApiResponseDto(
        int total_count,
        List<GithubSecretDto> secrets
) {
}
