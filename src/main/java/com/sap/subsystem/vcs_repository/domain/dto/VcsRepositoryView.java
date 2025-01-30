package com.sap.subsystem.vcs_repository.domain.dto;

/**
 * DTO view representation to the client
 *
 * @author danail.zlatanov
 * */
public record VcsRepositoryView(
        String businessId,
        String repository
) {
}
