package com.sap.subsystem.vcs_repository.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sap.subsystem.secret.domain.dto.SecretView;

import java.util.Set;

/**
 * DTO view representation to the client
 *
 * @author danail.zlatanov
 * */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record VcsRepositoryView(
        String businessId,
        String repository,
        Set<SecretView> secrets
) {
}
