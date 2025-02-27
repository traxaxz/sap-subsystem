package com.sap.subsystem.vcs_repository.domain.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

/**
 *<p>
 * This DTO is used to encapsulate repository-related data and ensure
 * that the repository field is not blank.
 *</p>
 * @author danail.zlatanov
 * */
public record VcsRepositoryDto(
        @NotBlank(message = "Repository field must not be empty")
        String repository,
        String secret
) {}
