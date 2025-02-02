package com.sap.subsystem.vcs_repository.domain.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

/**
 *<p>
 * This DTO is used to encapsulate repository-related data and ensure
 * that the repository field is not blank.
 *</p>
 * @author danail.zlatanov
 * */
public record EditVcsRepositoryDto(
        @NotBlank(message = "Repository field must not be empty")
        String repository,
        Set<UUID> secrets
) {}
