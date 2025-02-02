package com.sap.subsystem.secret.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record SecretView(
        UUID businessId,
        String secret
) {}
